/*
 * Copyright (c) 2021 SAP SE or an SAP affiliate company and XSK contributors
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, v2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * SPDX-FileCopyrightText: 2021 SAP SE or an SAP affiliate company and XSK contributors
 * SPDX-License-Identifier: Apache-2.0
 */
//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.11.26 at 10:54:28 AM EET 
//


package com.sap.ndb.bimodeldatafoundation;

import com.sap.ndb.searchmodelsearch.SearchableElementProperties;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * This is the base type for an attribute in the logical model. Attributes are used in
 * - Attribute views aka Dimensions
 * - Analytic views aka Cubes
 * - Calculation views (in the logical model part)
 * An attribute is either a (physical) Attribute containing mappings to physical column or	a CalculatedAttribute defined
 * via a formula in the expression	language
 *
 *
 * <p>Java class for AbstractAttribute complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="AbstractAttribute"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.sap.com/ndb/BiModelDataFoundation.ecore}LogicalColumn"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="attributeRelationship" type="{http://www.sap.com/ndb/BiModelDataFoundation.ecore}AttributeRelationship" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="searchProperties" type="{http://www.sap.com/ndb/SearchModelSearch.ecore}SearchableElementProperties" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="displayAttribute" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *       &lt;attribute name="attributeHierarchyActive" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" /&gt;
 *       &lt;attribute name="attributeHierarchyDefaultMember" type="{http://www.sap.com/ndb/BaseModelBase.ecore}DefaultMember" /&gt;
 *       &lt;attribute name="currency" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *       &lt;attribute name="unitOfMeasure" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *       &lt;attribute name="descriptionColumnName" type="{http://www.sap.com/ndb/BaseModelBase.ecore}DbName" /&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AbstractAttribute", propOrder = {
    "attributeRelationship",
    "searchProperties"
})
@XmlSeeAlso({
    Attribute.class,
    CalculatedAttribute.class
})
public abstract class AbstractAttribute
    extends LogicalColumn {

  protected List<AttributeRelationship> attributeRelationship;
  protected SearchableElementProperties searchProperties;
  @XmlAttribute(name = "displayAttribute")
  protected Boolean displayAttribute;
  @XmlAttribute(name = "attributeHierarchyActive")
  protected Boolean attributeHierarchyActive;
  @XmlAttribute(name = "attributeHierarchyDefaultMember")
  protected String attributeHierarchyDefaultMember;
  @XmlAttribute(name = "currency")
  protected Boolean currency;
  @XmlAttribute(name = "unitOfMeasure")
  protected Boolean unitOfMeasure;
  @XmlAttribute(name = "descriptionColumnName")
  @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
  protected String descriptionColumnName;

  /**
   * Gets the value of the attributeRelationship property.
   *
   * <p>
   * This accessor method returns a reference to the live list,
   * not a snapshot. Therefore any modification you make to the
   * returned list will be present inside the JAXB object.
   * This is why there is not a <CODE>set</CODE> method for the attributeRelationship property.
   *
   * <p>
   * For example, to add a new item, do as follows:
   * <pre>
   *    getAttributeRelationship().add(newItem);
   * </pre>
   *
   *
   * <p>
   * Objects of the following type(s) are allowed in the list
   * {@link AttributeRelationship }
   */
  public List<AttributeRelationship> getAttributeRelationship() {
    if (attributeRelationship == null) {
      attributeRelationship = new ArrayList<AttributeRelationship>();
    }
    return this.attributeRelationship;
  }

  /**
   * Gets the value of the searchProperties property.
   *
   * @return possible object is
   * {@link SearchableElementProperties }
   */
  public SearchableElementProperties getSearchProperties() {
    return searchProperties;
  }

  /**
   * Sets the value of the searchProperties property.
   *
   * @param value allowed object is
   *              {@link SearchableElementProperties }
   */
  public void setSearchProperties(SearchableElementProperties value) {
    this.searchProperties = value;
  }

  /**
   * Gets the value of the displayAttribute property.
   *
   * @return possible object is
   * {@link Boolean }
   */
  public Boolean isDisplayAttribute() {
    return displayAttribute;
  }

  /**
   * Sets the value of the displayAttribute property.
   *
   * @param value allowed object is
   *              {@link Boolean }
   */
  public void setDisplayAttribute(Boolean value) {
    this.displayAttribute = value;
  }

  /**
   * Gets the value of the attributeHierarchyActive property.
   *
   * @return possible object is
   * {@link Boolean }
   */
  public boolean isAttributeHierarchyActive() {
    if (attributeHierarchyActive == null) {
      return false;
    } else {
      return attributeHierarchyActive;
    }
  }

  /**
   * Sets the value of the attributeHierarchyActive property.
   *
   * @param value allowed object is
   *              {@link Boolean }
   */
  public void setAttributeHierarchyActive(Boolean value) {
    this.attributeHierarchyActive = value;
  }

  /**
   * Gets the value of the attributeHierarchyDefaultMember property.
   *
   * @return possible object is
   * {@link String }
   */
  public String getAttributeHierarchyDefaultMember() {
    return attributeHierarchyDefaultMember;
  }

  /**
   * Sets the value of the attributeHierarchyDefaultMember property.
   *
   * @param value allowed object is
   *              {@link String }
   */
  public void setAttributeHierarchyDefaultMember(String value) {
    this.attributeHierarchyDefaultMember = value;
  }

  /**
   * Gets the value of the currency property.
   *
   * @return possible object is
   * {@link Boolean }
   */
  public Boolean isCurrency() {
    return currency;
  }

  /**
   * Sets the value of the currency property.
   *
   * @param value allowed object is
   *              {@link Boolean }
   */
  public void setCurrency(Boolean value) {
    this.currency = value;
  }

  /**
   * Gets the value of the unitOfMeasure property.
   *
   * @return possible object is
   * {@link Boolean }
   */
  public Boolean isUnitOfMeasure() {
    return unitOfMeasure;
  }

  /**
   * Sets the value of the unitOfMeasure property.
   *
   * @param value allowed object is
   *              {@link Boolean }
   */
  public void setUnitOfMeasure(Boolean value) {
    this.unitOfMeasure = value;
  }

  /**
   * Gets the value of the descriptionColumnName property.
   *
   * @return possible object is
   * {@link String }
   */
  public String getDescriptionColumnName() {
    return descriptionColumnName;
  }

  /**
   * Sets the value of the descriptionColumnName property.
   *
   * @param value allowed object is
   *              {@link String }
   */
  public void setDescriptionColumnName(String value) {
    this.descriptionColumnName = value;
  }

}