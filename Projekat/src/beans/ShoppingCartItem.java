package beans;

public class ShoppingCartItem {
	private Article article;
	private int amount;
	
	public ShoppingCartItem() {
		super();
	}
	
	public ShoppingCartItem(Article article, int amount) {
		super();
		this.article = article;
		this.amount = amount;
	}
	public Article getArticle() {
		return article;
	}
	public void setArticle(Article article) {
		this.article = article;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
}
