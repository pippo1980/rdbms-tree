package com.sirius.algorithm.tree.preorder.domain.procedure;

import com.sirius.plugin.framework.jpa.domain.repository.JPAProcedure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;

/**
 * Created by pippo on 14-9-4.
 */
public class NodeShift implements JPAProcedure<Boolean> {

	private static final Logger LOGGER = LoggerFactory.getLogger(NodeShift.class);

	public NodeShift(Long afterRightPriority, Long priorityDistance) {
		this.after_right = afterRightPriority;
		this.distance = priorityDistance;
	}

	private Long after_right;

	private Long distance;

	@Override
	public Boolean execute(EntityManager entityManager) {

		try {
			entityManager.createQuery(
					"update TreeNode set leftPriority = leftPriority + :distance where leftPriority > :after_right")
					.setParameter("after_right", after_right)
					.setParameter("distance", distance).executeUpdate();

			entityManager.createQuery(
					"update TreeNode set rightPriority = rightPriority + :distance where rightPriority > :after_right")
					.setParameter("after_right", after_right)
					.setParameter("distance", distance).executeUpdate();

			return true;
		} catch (Exception e) {
			LOGGER.error("shift node due to error", e);
			return false;
		}

	}
}
