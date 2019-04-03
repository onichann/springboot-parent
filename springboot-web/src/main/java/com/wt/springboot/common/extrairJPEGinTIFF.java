package com.wt.springboot.common; /**
 * @(#)extrairJPEGinTIFF.java
 *
 * 旧tif Decoding of old style JPEG-in-TIFF data is not supported.问题
 * @author Vinicius Alves das Neves  e-mail:viniciusba@gmail.com
 * @version 1.00 2007/3/8
 */
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Vector;
import java.awt.Point;

public class extrairJPEGinTIFF {

    private FileInputStream imgTIFF=null;
    private byte[] bytesTIFF=null;
    private int qdeImagens=0;
    private Vector pontosIMG=null;
    private boolean FileExiste=false;

    public extrairJPEGinTIFF() {

    }

    /************************************************************************/
    public boolean setArqTIFF(String file){
        try{

            imgTIFF = new FileInputStream(file);
            int tam=imgTIFF.available();
            System.out.println(tam);
            bytesTIFF=new byte[tam];
            imgTIFF.read(bytesTIFF,0,tam);

        }catch(IOException erro){
            System.out.println(erro.toString());
        }
        pontosIMG=new Vector();
        FileExiste=getPosicaoJPEG();
        return FileExiste;
    }

    /************************************************************************/
    public boolean isExisteJPEG(){
        return FileExiste;
    }

    /************************************************************************/
    private boolean getPosicaoJPEG(){

        int inicio=0;int fim=0;String ant="";
        Vector imgsPontos=new Vector();

        for(int i=0;i<bytesTIFF.length;i++){

            String atual=paraStringHexaByte(bytesTIFF[i]);

            atual=atual.toUpperCase();



            /**
             * Buscando cabe�alhos de inicio e fim de uma imagem JFIF/JPEG
             * Inicio: FF D8 FF E0...
             * Fim: FF D9
             */

//Cabe�alho de inicio de imagem

            if(ant.equals("FF") && atual.equals("D8")){



                String pos1=paraStringHexaByte(bytesTIFF[i+1]).toUpperCase();

                String pos2=paraStringHexaByte(bytesTIFF[i+2]).toUpperCase();



                if(pos1.equals("FF") && pos2.equals("E0") )

                {

                    System.out.println(pos1+"-"+pos2);

                    inicio=i-1;ant=null;

                }

                else ant=null;

            }//Cabe�alho de fim de imagem

            else if(inicio!=0 && ant.equals("FF") && atual.equals("D9")){



                fim=i-1;ant=null;



            }

            //--------------------------------------------------------------------



            ant=atual;



            //Valida marcadores de inicio e fim de uma imagem JFIF/JPEG

            if(inicio!=0 && fim!=0){

                imgsPontos.add(new Point(inicio,fim));

                inicio=0;fim=0;ant="";atual="";

            };



        }//Fim for(int i=0;i<bytesTIFF.length;i++)



        //Verifica se encontrou alguma imagem

        if(imgsPontos.size()>0){

            pontosIMG=imgsPontos;

            return true;

        } else return false;



    }



    /************************************************************************/

    private String paraStringHexaByte(byte bytes) {

        StringBuilder s = new StringBuilder();



        int parteAlta = ((bytes >> 4) & 0xf) << 4;

        int parteBaixa = bytes & 0xf;

        if (parteAlta == 0) s.append('0');



        String strHex=Integer.toHexString(parteAlta | parteBaixa);

        s.append(strHex);



        return s.toString();

    }



    /************************************************************************/

    private byte[] getJPEGinTIFF(byte[] imgBytes,int setI,int setF){

        ByteArrayOutputStream bytesExtraidos=null;

        try{

            bytesExtraidos = new ByteArrayOutputStream();

            bytesExtraidos.write(imgBytes,setI,setF-setI);

        }catch(Exception e){System.out.println(e.toString());}


        assert bytesExtraidos != null;
        return bytesExtraidos.toByteArray();

    }



    /************************************************************************/

    public byte[] getJPEG(int numPagina){



        if(numPagina>=0 && pontosIMG.size()>0){

            byte[] temp=getJPEGinTIFF(bytesTIFF,

                    ((Point)pontosIMG.elementAt(numPagina)).x,

                    ((Point)pontosIMG.elementAt(numPagina)).y

            );

            return temp;

        }else return null;



    }



    /************************************************************************/

    public void salvarJPEG(int numPagina){

        try{



            FileOutputStream saida=new FileOutputStream("e:\\saida"+numPagina+".jpg");

            saida.write(getJPEG(numPagina));

            saida.close();



        }catch(IOException e){System.out.println(e.toString());}



    }



    /************************************************************************/

    private int getQdeImagens(){

        return pontosIMG.size();

    }

    /************************************************************************/



    public static void main(String args[]){


        extrairJPEGinTIFF teste =new extrairJPEGinTIFF();

        boolean result=teste.setArqTIFF("e:\\G027-YW0501-2016-0008-001.tif");

        System.out.println(teste.getQdeImagens());

        if(result==true) teste.salvarJPEG(0);



    }



}
