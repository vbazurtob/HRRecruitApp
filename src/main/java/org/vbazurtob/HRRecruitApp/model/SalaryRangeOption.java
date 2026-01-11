/*
 * Personal License Agreement
 * Copyright Notice
 *
 * © 2025 Voltaire Bazurto Blacio. All rights reserved.
 * License Terms
 *
 *     Ownership: All code contained in this portfolio is the sole property of Voltaire Bazurto Blacio and is hereby copyrighted by me.
 *
 *     Permitted Use: Others are welcome to view and study the code for personal, educational, or non-commercial purposes. You may share insights or information about the code, but you cannot use it for any commercial products, either as-is or in a derivative form.
 *
 *     Restrictions: The code may not be used, reproduced, or distributed for commercial purposes under any circumstances without my explicit written permission.
 *
 *     Rights Reserved: I reserve the right to use the code, or any future versions thereof, for my own purposes in any way I choose, including but not limited to the development of future commercial derivative works under my name or personal brand.
 *
 *     Disclaimer: The code is provided "as is" without warranty of any kind, either express or implied. I am not responsible for any damages resulting from the use of this code.
 *
 * By accessing this portfolio, you agree to abide by these terms.
 */

package org.vbazurtob.HRRecruitApp.model;

public class SalaryRangeOption {

  @Override
  public String toString() {
    return "SalaryRangeOption [id="
        + id
        + ", text="
        + text
        + ", salaryEqualsMore="
        + salaryEqualsMore
        + ", salaryLessEquals="
        + salaryLessEquals
        + "]";
  }

  private int id;
  private String text;
  private int salaryEqualsMore;
  private int salaryLessEquals;

  public SalaryRangeOption() {
    // TODO Auto-generated constructor stub
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public int getSalaryEqualsMore() {
    return salaryEqualsMore;
  }

  public void setSalaryEqualsMore(int salaryEqualsMore) {
    this.salaryEqualsMore = salaryEqualsMore;
  }

  public int getSalaryLessEquals() {
    return salaryLessEquals;
  }

  public void setSalaryLessEquals(int salaryLessEquals) {
    this.salaryLessEquals = salaryLessEquals;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + id;
    result = prime * result + salaryEqualsMore;
    result = prime * result + salaryLessEquals;
    result = prime * result + ((text == null) ? 0 : text.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    SalaryRangeOption other = (SalaryRangeOption) obj;
    if (id != other.id) return false;
    if (salaryEqualsMore != other.salaryEqualsMore) return false;
    if (salaryLessEquals != other.salaryLessEquals) return false;
    if (text == null) {
      return other.text == null;
    } else return text.equals(other.text);
  }

  public SalaryRangeOption(int id, String text, int salaryEqualsMore, int salaryLessEquals) {
    super();
    this.id = id;
    this.text = text;
    this.salaryEqualsMore = salaryEqualsMore;
    this.salaryLessEquals = salaryLessEquals;
  }
}
