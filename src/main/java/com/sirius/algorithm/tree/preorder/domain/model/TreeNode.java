package com.sirius.algorithm.tree.preorder.domain.model;

import com.sirius.plugin.framework.jpa.domain.model.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Set;

/**
 * @author pippo
 * @since 2013-5-13
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "tree_t")
public class TreeNode extends BaseEntity {

	private static final long serialVersionUID = 7338733603388872363L;

	public static final String root_id = "root";

	@Transient
	public boolean isLeaf() {
		return rightPriority - leftPriority == 1;
	}

	private String name;

	@Column(name = "left_p")
	@Index(name = "i_left_p")
	private Long leftPriority;

	@Column(name = "right_p")
	@Index(name = "i_right_p")
	private Long rightPriority;

	private Integer depth;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	//使用数据库外键来进行级联删除,避免orphanRemoval的级联删除产生无数sql
	@OnDelete(action = OnDeleteAction.CASCADE)
	private TreeNode parent;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
	//@OrderBy("leftPriority")
	//集合的二级缓存开启需要在属性上声明
	private Set<TreeNode> children;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getLeftPriority() {
		return leftPriority;
	}

	public void setLeftPriority(Long leftPriority) {
		this.leftPriority = leftPriority;
	}

	public Long getRightPriority() {
		return rightPriority;
	}

	public void setRightPriority(Long rightPriority) {
		this.rightPriority = rightPriority;
	}

	public Integer getDepth() {
		return depth;
	}

	public void setDepth(Integer depth) {
		this.depth = depth;
	}

	public TreeNode getParent() {
		return parent;
	}

	public void setParent(TreeNode parent) {
		this.parent = parent;
	}

	public Set<TreeNode> getChildren() {
		return children;
	}

	public void setChildren(Set<TreeNode> children) {
		this.children = children;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("name", name)
				.append("leftPriority", leftPriority)
				.append("rightPriority", rightPriority)
				.append("depth", depth)
				.append("parent", parent)
				.append("children", children)
				.toString();
	}
}
