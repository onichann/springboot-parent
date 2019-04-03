package com.wt.springboot.data;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ArticleService_PagingAndSorting {
	
	// 注入数据访问层接口对象 
	@Resource
	private ArticleRepository_PagingAndSorting articleRepository;
	
	public Iterable<Article_PagingAndSorting> findAllSort(Sort sort) {
		return articleRepository.findAll(sort);
	}

	public Page<Article_PagingAndSorting> findAll(Pageable page) {
		return articleRepository.findAll(page);
	}

}
