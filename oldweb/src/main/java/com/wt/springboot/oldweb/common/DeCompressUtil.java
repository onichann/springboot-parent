package com.wt.springboot.oldweb.common;

import de.innosystec.unrar.Archive;
import de.innosystec.unrar.rarfile.FileHeader;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Expand;

import java.io.File;
import java.io.FileOutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeCompressUtil {
	private static void unzip(String sourceZip, String destDir)
			throws Exception {
		try {
			Project p = new Project();
			Expand e = new Expand();
			e.setProject(p);
			e.setSrc(new File(sourceZip));
			e.setOverwrite(false);
			e.setDest(new File(destDir));
			e.setEncoding("gbk");
			e.execute();
		} catch (Exception e) {
			throw e;
		}
	}
	private static void unrar(String sourceRar, String destDir)
			throws Exception {
		Archive a = null;
		FileOutputStream fos = null;
		try {
			a = new Archive(new File(sourceRar));
			FileHeader fh = a.nextFileHeader();
			while (fh != null) {
				if (!fh.isDirectory()) {
					String compressFileName = fh.getFileNameW().trim();
					if (!existZH(compressFileName)) {
						compressFileName = fh.getFileNameString().trim();
					}
					
					String destFileName = "";
					String destDirName = "";
					if (File.separator.equals("/")) {
						destFileName = destDir
								+ compressFileName.replaceAll("\\\\", "/");
						destDirName = destFileName.substring(0, destFileName
								.lastIndexOf("/"));
					} else {
						destFileName = destDir
								+ compressFileName.replaceAll("/", "\\\\");
						destDirName = destFileName.substring(0, destFileName
								.lastIndexOf("\\"));
					}
					File dir = new File(destDirName);
					if (!dir.exists() || !dir.isDirectory()) {
						dir.mkdirs();
					}
					fos = new FileOutputStream(new File(destFileName));
					a.extractFile(fh, fos);
					fos.close();
					fos = null;
				}
				fh = a.nextFileHeader();
			}
			a.close();
			a = null;
		} catch (Exception e) {
			throw e;
		} finally {
			if (fos != null) {
				try {
					fos.close();
					fos = null;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (a != null) {
				try {
					a.close();
					a = null;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 解压缩
	 */
	public static void deCompress(String sourceFile, String destDir)
			throws Exception {
		// 保证文件夹路径最后是"/"或者"\"
		char lastChar = destDir.charAt(destDir.length() - 1);
		if (lastChar != '/' && lastChar != '\\') {
			destDir += File.separator;
		}
		String type = sourceFile.substring(sourceFile.lastIndexOf(".") + 1);
		if (type.equals("zip")) {
			DeCompressUtil.unzip(sourceFile, destDir);
		} else if (type.equals("rar")) {
			DeCompressUtil.unrar(sourceFile, destDir);
		} else {
			throw new Exception("只支持zip和rar格式的压缩包！");
		}
	}

	public static boolean existZH(String str) {
		String regEx = "[\\u4e00-\\u9fa5]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		while (m.find()) {
			return true;
		}
		return false;
	}

	public static void main(String[] args) {
		try {
			System.out.println(1);
			DeCompressUtil.deCompress("g:/测试上传数据.zip", "g:/测试上传数据");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
