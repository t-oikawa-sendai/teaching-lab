// コード15-5 データをまとめるクラスの例
package com.example.demo.model; 

public class Product {
private String name;
private int price;
// 引数を持たないコンストラクタ
public Product() {} 
// すべてのフィールドを引数にもつコンストラクタ
public Product(String name, int price) { 
this.name = name;
this.price = price;
}
// getterとsetter
public String getName() { return name; }
public void setName(String name) { this.name = name; }
public int getPrice() { return price; }
public void setPrice(int price) { this.price = price; }
}
Product.java
（com.example.demo.modelパッケージ）