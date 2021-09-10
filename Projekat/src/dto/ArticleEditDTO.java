package dto;

import beans.Article;

public class ArticleEditDTO {
	private String oldArticleName;
	private Article article;
	
	public ArticleEditDTO() {
		
	}
	public ArticleEditDTO(String oldArticleName, Article article) {
		super();
		this.oldArticleName = oldArticleName;
		this.article = article;
	}
	public String getOldArticleName() {
		return oldArticleName;
	}
	public void setOldArticleName(String oldArticleName) {
		this.oldArticleName = oldArticleName;
	}
	public Article getArticle() {
		return article;
	}
	public void setArticle(Article article) {
		this.article = article;
	}
	
}
