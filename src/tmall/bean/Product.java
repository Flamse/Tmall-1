package tmall.bean;

import java.util.Date;
import java.util.List;

public class Product {
	private int id;
	private String name;
	private String subTitle;
	private float originalPrice;
	private float promotePrice;
	private int stock;
	private Category category;
	private Date createDate;
	//��ƷͼƬ���
	private List<ProductImage> productImages;
	private List<ProductImage> productSingleImages;
	private List<ProductImage>	productDetailImages;
	private ProductImage firstProductImage;
	
	private int reviewCount;
	private int saleCount;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSubTitle() {
		return subTitle;
	}
	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}
	public float getOriginalPrice() {
		return originalPrice;
	}
	public void setOriginalPrice(float originalPrice) {
		this.originalPrice = originalPrice;
	}
	public float getPromotePrice() {
		return promotePrice;
	}
	public void setPromotePrice(float promotePrice) {
		this.promotePrice = promotePrice;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public List<ProductImage> getProductImages() {
		return productImages;
	}
	public void setProductImages(List<ProductImage> productImages) {
		this.productImages = productImages;
	}
	public List<ProductImage> getProductSingleImages() {
		return productSingleImages;
	}
	public void setProductSingleImages(List<ProductImage> productSingleImages) {
		this.productSingleImages = productSingleImages;
	}
	public List<ProductImage> getProductDetailImages() {
		return productDetailImages;
	}
	public void setProductDetailImages(List<ProductImage> productDetailImages) {
		this.productDetailImages = productDetailImages;
	}
	public ProductImage getFirstProductImage() {
		return firstProductImage;
	}
	public void setFirstProductImage(ProductImage firstProductImage) {
		this.firstProductImage = firstProductImage;
	}
	public int getReviewCount() {
		return reviewCount;
	}
	public void setReviewCount(int reviewCount) {
		this.reviewCount = reviewCount;
	}
	public int getSaleCount() {
		return saleCount;
	}
	public void setSaleCount(int saleCount) {
		this.saleCount = saleCount;
	}
	
	
}
