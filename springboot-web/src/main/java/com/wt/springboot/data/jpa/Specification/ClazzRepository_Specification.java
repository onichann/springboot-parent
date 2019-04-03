package com.wt.springboot.data.jpa.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
/**
 * @Author dlei(徐磊)
 * @Email dlei0009@163.com
 */
public interface ClazzRepository_Specification extends JpaRepository<Clazz_Specification, Integer> ,JpaSpecificationExecutor<Clazz_Specification>{
	
}
