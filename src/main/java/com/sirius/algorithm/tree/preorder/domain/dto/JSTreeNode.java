package com.sirius.algorithm.tree.preorder.domain.dto;

import com.sirius.algorithm.tree.preorder.domain.model.TreeNode;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by pippo on 14-10-12.
 */
public class JSTreeNode {

	public JSTreeNode() {

	}

	public JSTreeNode(TreeNode node) {
		if (node == null) {
			id = "#";
			return;
		}

		id = node.getId();
		parent = node.getParent() != null ? node.getParent().getId() : null;
		text = String.format("%s[%s-%s]", node.getName(), node.getLeftPriority(), node.getRightPriority());
		children = !node.isLeaf();
		a_attr.put("leftPriority", node.getLeftPriority());
		a_attr.put("rightPriority", node.getRightPriority());
	}

	private String id;

	private String parent;

	private String text;

	private String icon;

	private State state = new State();

	private boolean children = false;

	private Map<String, Object> li_attr = new HashMap<>();

	private Map<String, Object> a_attr = new HashMap<>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public boolean isChildren() {
		return children;
	}

	public void setChildren(boolean children) {
		this.children = children;
	}

	public Map<String, Object> getLi_attr() {
		return li_attr;
	}

	public void setLi_attr(Map<String, Object> li_attr) {
		this.li_attr = li_attr;
	}

	public Map<String, Object> getA_attr() {
		return a_attr;
	}

	public void setA_attr(Map<String, Object> a_attr) {
		this.a_attr = a_attr;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("id", id)
				.append("parent", parent)
				.append("text", text)
				.append("icon", icon)
				.append("state", state)
				.append("children", children)
				.append("li_attr", li_attr)
				.append("a_attr", a_attr)
				.toString();
	}

	public static class State {

		private boolean opened = false;

		private boolean disabled = false;

		private boolean selected = false;

		public boolean isOpened() {
			return opened;
		}

		public void setOpened(boolean opened) {
			this.opened = opened;
		}

		public boolean isDisabled() {
			return disabled;
		}

		public void setDisabled(boolean disabled) {
			this.disabled = disabled;
		}

		public boolean isSelected() {
			return selected;
		}

		public void setSelected(boolean selected) {
			this.selected = selected;
		}

		@Override
		public String toString() {
			return new ToStringBuilder(this)
					.append("opened", opened)
					.append("disabled", disabled)
					.append("selected", selected)
					.toString();
		}
	}

}
