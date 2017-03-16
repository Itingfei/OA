package com.dongao.oa.pojo;

import java.math.BigDecimal;
import javax.persistence.*;

@Table(name = "da_category_item")
public class CategoryItem extends BaseEntity {
    @Column(name = "category_id")
    private Long categoryId;
    /**
     * 采购物品分类ID
     */
    @Column(name = "classify_id")
    private Long classifyId;

    /**
     * 采购物品名称
     */
    @Column(name = "Item_name")
    private String itemName;

    /**
     * 需求数量
     */
    private Integer needcount;

    /**
     * 采购数量
     */
    private Integer count;

    /**
     * 库存量
     */
    private Integer stock;

    /**
     * 型号规格
     */
    private String model;

    /**
     * 申请单价
     */
    @Column(name = "apply_price")
    private BigDecimal applyPrice;

    /**
     * 实际单价
     */
    @Column(name = "actual_price")
    private BigDecimal actualPrice;

    /**
     * 表单项备注
     */
    @Column(name = "item_remarks")
    private String itemRemarks;
    /**
     * 删除状态0未删除，1，已删除
     */
    @Column(name = "delete_flag")
    private Integer deleteFlag = 0;
    @Transient
    private String classifyName;
    /**
     * @return category_id
     */
    public Long getCategoryId() {
        return categoryId;
    }

    /**
     * @param categoryId
     */
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * 获取采购物品名称
     *
     * @return Item_name - 采购物品名称
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * 设置采购物品名称
     *
     * @param itemName 采购物品名称
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * 获取需求数量
     *
     * @return needcount - 需求数量
     */
    public Integer getNeedcount() {
        return needcount;
    }

    /**
     * 设置需求数量
     *
     * @param needcount 需求数量
     */
    public void setNeedcount(Integer needcount) {
        this.needcount = needcount;
    }

    /**
     * 获取采购数量
     *
     * @return count - 采购数量
     */
    public Integer getCount() {
        return count;
    }

    /**
     * 设置采购数量
     *
     * @param count 采购数量
     */
    public void setCount(Integer count) {
        this.count = count;
    }

    /**
     * 获取库存量
     *
     * @return stock - 库存量
     */
    public Integer getStock() {
        return stock;
    }

    /**
     * 设置库存量
     *
     * @param stock 库存量
     */
    public void setStock(Integer stock) {
        this.stock = stock;
    }

    /**
     * 获取型号规格
     *
     * @return model - 型号规格
     */
    public String getModel() {
        return model;
    }

    /**
     * 设置型号规格
     *
     * @param model 型号规格
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * 获取申请单价
     *
     * @return apply_price - 申请单价
     */
    public BigDecimal getApplyPrice() {
        return applyPrice;
    }

    /**
     * 设置申请单价
     *
     * @param applyPrice 申请单价
     */
    public void setApplyPrice(BigDecimal applyPrice) {
        this.applyPrice = applyPrice;
    }

    /**
     * 获取实际单价
     *
     * @return actual_price - 实际单价
     */
    public BigDecimal getActualPrice() {
        return actualPrice;
    }

    /**
     * 设置实际单价
     *
     * @param actualPrice 实际单价
     */
    public void setActualPrice(BigDecimal actualPrice) {
        this.actualPrice = actualPrice;
    }

    /**
     * 获取表单项备注
     *
     * @return item_remarks - 表单项备注
     */
    public String getItemRemarks() {
        return itemRemarks;
    }

    /**
     * 设置表单项备注
     *
     * @param itemRemarks 表单项备注
     */
    public void setItemRemarks(String itemRemarks) {
        this.itemRemarks = itemRemarks;
    }

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public Long getClassifyId() {
        return classifyId;
    }

    public void setClassifyId(Long classifyId) {
        this.classifyId = classifyId;
    }

    public String getClassifyName() {
        return classifyName;
    }

    public void setClassifyName(String classifyName) {
        this.classifyName = classifyName;
    }

    public CategoryItem(){

    }
}