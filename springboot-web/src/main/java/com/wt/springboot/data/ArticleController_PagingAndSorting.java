package com.wt.springboot.data;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController_PagingAndSorting {
	
	// 注入ArticleService
	@Resource
	private ArticleService_PagingAndSorting articleService;

	@RequestMapping("/sort")
	public Iterable<Article_PagingAndSorting> sortArticle() {
		// 指定排序参数对象：根据id，进行降序查询
		Sort sort = new Sort(Sort.Direction.DESC, "id");
		Iterable<Article_PagingAndSorting> articleDatas = articleService.findAllSort(sort);
		return articleDatas;
	}

	@RequestMapping("/pager")
	public List<Article_PagingAndSorting> sortPagerArticle(int pageIndex) {
		// 指定排序参数对象：根据id，进行降序查询
		Sort sort = new Sort(Sort.Direction.DESC, "id");
		/**
		 * 封装分页实体 参数一：pageIndex表示当前查询的第几页(默认从0开始，0表示第一页) 参数二：表示每页展示多少数据，现在设置每页展示2条数据
		 * 参数三：封装排序对象，根据该对象的参数指定根据id降序查询
		 * */
		Pageable page = PageRequest.of(pageIndex - 1, 2, sort);
		Page<Article_PagingAndSorting> articleDatas = articleService.findAll(page);
		System.out.println("查询总页数:" + articleDatas.getTotalPages());
		System.out.println("查询总记录数:" + articleDatas.getTotalElements());
		System.out.println("查询当前第几页:" + articleDatas.getNumber() + 1);
		System.out.println("查询当前页面的记录数:" + articleDatas.getNumberOfElements());
		// 查询出的结果数据集合
		List<Article_PagingAndSorting> articles = articleDatas.getContent();
		System.out.println("查询当前页面的集合:" + articles);
		return articles;
	}
}
