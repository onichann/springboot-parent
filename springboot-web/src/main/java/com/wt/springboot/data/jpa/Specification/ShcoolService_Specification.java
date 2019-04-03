package com.wt.springboot.data.jpa.Specification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class ShcoolService_Specification {
	// 注入数据访问层接口对象 
	@Resource
	private StudentRepository_Specification studentRepository;
	@Resource
	private ClazzRepository_Specification clazzRepository;
	@Transactional
	public void saveClazzAll(List<Clazz_Specification> clazzs) {
		clazzRepository.saveAll(clazzs);
	}
	@Transactional
	public void saveStudentAll(List<Student_Specification> students) {
		studentRepository.saveAll(students);
	}
	
	/**
	 * 根据性别查询学生信息
	 * @param
	 * @return
	 */
	@SuppressWarnings("serial")
	public List<Map<String, Object>> getStusBySex(char sex) {
		List<Student_Specification> students = studentRepository.findAll(new Specification<Student_Specification>() {

			@Override
			public Predicate toPredicate(Root<Student_Specification> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
			   // root.get("sex")表示获取sex这个字段名称,equal表示执行equal查询
			   // 相当于 select s from Student_Specification s where s.sex = ?1
               Predicate p1 = cb.equal(root.get("sex"), sex);
               return p1;
			}
		});
		List<Map<String, Object>>  results = new ArrayList<>(); 
		// 遍历查询出的学生对象，提取姓名，年龄，性别信息
		for(Student_Specification student:students){
			Map<String , Object> stu = new HashMap<>(); 
			stu.put("name", student.getName());
			stu.put("age", student.getAge());
			stu.put("sex", student.getSex());
			results.add(stu);
		}
		return results;
	}
	
	
	/**
	 * 动态查询学生信息 ：可以根据学生对象的姓名(模糊匹配)， 地址查询(模糊匹配),性别,班级查询学生信息 
	 *               如果没有传输参数,默认查询所有的学生信息
	 * @param
	 * @return
	 */
	@SuppressWarnings("serial")
	public List<Map<String, Object>> getStusByDynamic(Student_Specification student) {
		List<Student_Specification> students = studentRepository.findAll(new Specification<Student_Specification>() {
			@Override
			public Predicate toPredicate(Root<Student_Specification> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				// 本集合用于封装查询条件
				List<Predicate> predicates = new ArrayList<Predicate>();
				if(student!=null){
					/** 是否传入了姓名来查询  */
					if(!StringUtils.isEmpty(student.getName())){
						predicates.add(cb.like(root.<String> get("name"),"%" + student.getName() + "%"));
					}
					/** 是否传入了地址来查询  */
					if(!StringUtils.isEmpty(student.getAddress())){
						predicates.add(cb.like(root.<String> get("address"),"%" + student.getAddress() + "%"));
					}
					/** 是否传入了性别来查询 */
					if(student.getSex() != '\0'){
						predicates.add(cb.equal(root.<String> get("sex"),student.getSex()));
					}
					/** 判断是否传入了班级信息来查询 */
					if(student.getClazz()!=null && !StringUtils.isEmpty(student.getClazz().getName())){
						root.join("clazz", JoinType.INNER);
						Path<String> clazzName = root.get("clazz").get("name");
						predicates.add(cb.equal(clazzName, student.getClazz().getName()));
					}
				}
				return query.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
			}
		});
		List<Map<String, Object>>  results = new ArrayList<>(); 
		// 遍历查询出的学生对象，提取姓名，年龄，性别信息
		for(Student_Specification stu :students){
			Map<String , Object> stuMap = new HashMap<>(); 
			stuMap.put("name", stu.getName());
			stuMap.put("age", stu.getAge());
			stuMap.put("sex", stu.getSex());
			stuMap.put("address", stu.getAddress());
			stuMap.put("clazzName", stu.getClazz().getName());
			results.add(stuMap);
		}
		return results;
	}
	/**
	 * 分页查询某个班级的学生信息
	 * @param clazzName 班级名称
	 * @param pageIndex 代表当前查询第几页 
	 * @param pageSize 代表每页查询的最大数据量
	 * @return
	 */
	@SuppressWarnings("serial")
	public Page<Student_Specification> getStusByPage(String clazzName , int pageIndex , int pageSize ) {
		// 指定排序参数对象：根据id，进行降序查询
		Sort sort = new Sort(Sort.Direction.DESC, "id");
		// 分页查询学生信息，返回分页实体对象数据
		// pages对象中包含了查询出来的数据信息，以及与分页相关的信息
		Page<Student_Specification> pages = studentRepository.findAll(new Specification<Student_Specification>() {
			@Override
			public Predicate toPredicate(Root<Student_Specification> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				root.join("clazz", JoinType.INNER);
				Path<String> cn = root.get("clazz").get("name");
	            Predicate p1 = cb.equal(cn, clazzName);
	            return p1 ;
			}
		},PageRequest.of(pageIndex-1, pageSize, sort));
		return pages;
	}
	
}
