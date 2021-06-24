package dao;

import java.util.ArrayList;

import beans.Article;


public class ArticleDao {
	private ArrayList<Article> articles;

	public ArticleDao(ArrayList<Article> articles) {
		super();
		this.articles = articles;
	}

	public ArrayList<Article> getArticles() {
		return articles;
	}

	public void setArticles(ArrayList<Article> articles) {
		this.articles = articles;
	}
	
	

}
