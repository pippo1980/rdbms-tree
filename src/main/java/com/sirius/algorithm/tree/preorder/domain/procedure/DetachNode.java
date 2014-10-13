package com.sirius.algorithm.tree.preorder.domain.procedure;

import com.sirius.algorithm.tree.preorder.domain.model.TreeNode;
import com.sirius.plugin.framework.jpa.domain.repository.JPAProcedure;

import javax.persistence.EntityManager;

/**
 * Created by pippo on 14-10-13.
 */
public class DetachNode implements JPAProcedure<Boolean> {

	private static final String RESET_PRIORITY = "update TreeNode set leftPriority=-leftPriority, rightPriority=-rightPriority where leftPriority>=:left_p and rightPriority<=:right_p";

	public DetachNode(TreeNode node) {
		this.node = node;
	}

	private TreeNode node;

	@Override
	public Boolean execute(EntityManager entityManager) {
		if (node == null) {
			return false;
		}

		/*将要移动的子树的所有节点的左右权值置为当前值的复数*/
		entityManager.createQuery(RESET_PRIORITY)
				.setParameter("left_p", node.getLeftPriority())
				.setParameter("right_p", node.getRightPriority())
				.executeUpdate();

		return true;
	}
}
