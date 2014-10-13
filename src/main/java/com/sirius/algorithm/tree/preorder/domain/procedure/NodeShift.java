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
	private static final String UPDATE_LEFT = "update TreeNode set leftPriority = leftPriority + :distance where leftPriority > :after_right";
	private static final String UPDATE_RIGHT = "update TreeNode set rightPriority = rightPriority + :distance where rightPriority > :after_right";

	public NodeShift(Long afterRightPriority, Long priorityDistance) {
		this.after_right = afterRightPriority;
		this.distance = priorityDistance;
	}

	private Long after_right;

	private Long distance;

	@Override
	public Boolean execute(EntityManager entityManager) {

		try {
			entityManager.createQuery(UPDATE_LEFT)
					.setParameter("after_right", after_right)
					.setParameter("distance", distance).executeUpdate();

			entityManager.createQuery(UPDATE_RIGHT)
					.setParameter("after_right", after_right)
					.setParameter("distance", distance).executeUpdate();

			return true;
		} catch (Exception e) {
			LOGGER.error("shift node due to error", e);
			return false;
		}

	}
}
