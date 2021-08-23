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


package com.sap.ndb.bimodeldimension;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ParentChildHierarchy complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="ParentChildHierarchy"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.sap.com/ndb/BiModelDimension.ecore}Hierarchy"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="attributeParentPair" type="{http://www.sap.com/ndb/BiModelDimension.ecore}AttributeParentPair" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="siblingOrder" type="{http://www.sap.com/ndb/BiModelDimension.ecore}SiblingOrder" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="timeProperties" type="{http://www.sap.com/ndb/BiModelDimension.ecore}HierarchyTimeProperties" minOccurs="0"/&gt;
 *         &lt;element name="edgeAttribute" type="{http://www.sap.com/ndb/BiModelDimension.ecore}EdgeAttribute" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ParentChildHierarchy", propOrder = {
    "attributeParentPair",
    "siblingOrder",
    "timeProperties",
    "edgeAttribute"
})
public class ParentChildHierarchy
    extends Hierarchy {

  protected List<AttributeParentPair> attributeParentPair;
  protected List<SiblingOrder> siblingOrder;
  protected HierarchyTimeProperties timeProperties;
  protected List<EdgeAttribute> edgeAttribute;

  /**
   * Gets the value of the attributeParentPair property.
   *
   * <p>
   * This accessor method returns a reference to the live list,
   * not a snapshot. Therefore any modification you make to the
   * returned list will be present inside the JAXB object.
   * This is why there is not a <CODE>set</CODE> method for the attributeParentPair property.
   *
   * <p>
   * For example, to add a new item, do as follows:
   * <pre>
   *    getAttributeParentPair().add(newItem);
   * </pre>
   *
   *
   * <p>
   * Objects of the following type(s) are allowed in the list
   * {@link AttributeParentPair }
   */
  public List<AttributeParentPair> getAttributeParentPair() {
    if (attributeParentPair == null) {
      attributeParentPair = new ArrayList<AttributeParentPair>();
    }
    return this.attributeParentPair;
  }

  /**
   * Gets the value of the siblingOrder property.
   *
   * <p>
   * This accessor method returns a reference to the live list,
   * not a snapshot. Therefore any modification you make to the
   * returned list will be present inside the JAXB object.
   * This is why there is not a <CODE>set</CODE> method for the siblingOrder property.
   *
   * <p>
   * For example, to add a new item, do as follows:
   * <pre>
   *    getSiblingOrder().add(newItem);
   * </pre>
   *
   *
   * <p>
   * Objects of the following type(s) are allowed in the list
   * {@link SiblingOrder }
   */
  public List<SiblingOrder> getSiblingOrder() {
    if (siblingOrder == null) {
      siblingOrder = new ArrayList<SiblingOrder>();
    }
    return this.siblingOrder;
  }

  /**
   * Gets the value of the timeProperties property.
   *
   * @return possible object is
   * {@link HierarchyTimeProperties }
   */
  public HierarchyTimeProperties getTimeProperties() {
    return timeProperties;
  }

  /**
   * Sets the value of the timeProperties property.
   *
   * @param value allowed object is
   *              {@link HierarchyTimeProperties }
   */
  public void setTimeProperties(HierarchyTimeProperties value) {
    this.timeProperties = value;
  }

  /**
   * Gets the value of the edgeAttribute property.
   *
   * <p>
   * This accessor method returns a reference to the live list,
   * not a snapshot. Therefore any modification you make to the
   * returned list will be present inside the JAXB object.
   * This is why there is not a <CODE>set</CODE> method for the edgeAttribute property.
   *
   * <p>
   * For example, to add a new item, do as follows:
   * <pre>
   *    getEdgeAttribute().add(newItem);
   * </pre>
   *
   *
   * <p>
   * Objects of the following type(s) are allowed in the list
   * {@link EdgeAttribute }
   */
  public List<EdgeAttribute> getEdgeAttribute() {
    if (edgeAttribute == null) {
      edgeAttribute = new ArrayList<EdgeAttribute>();
    }
    return this.edgeAttribute;
  }

}