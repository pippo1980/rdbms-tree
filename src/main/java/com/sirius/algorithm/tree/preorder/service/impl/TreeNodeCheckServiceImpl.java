package com.sirius.algorithm.tree.preorder.service.impl;

import com.sirius.algorithm.tree.preorder.domain.model.TreeNode;
import com.sirius.algorithm.tree.preorder.domain.repository.TreeNodeRepository;
import com.sirius.algorithm.tree.preorder.service.TreeNodeCheckService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

/**
 * Created by pippo on 14-10-13.
 */
@Service
@Transactional(readOnly = true)
public class TreeNodeCheckServiceImpl implements TreeNodeCheckService {

	private static final Logger logger = LoggerFactory.getLogger(TreeNodeCheckServiceImpl.class);

	/* 可以允许任务拆分为cpu的个数,并行计算 */
	private static final ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());

	@Resource
	protected TreeNodeRepository treeNodeRepository;

	@Override
	public List<TreeNode> check() throws Exception {
		List<TreeNode> invalidNodes = new ArrayList<>();
		long time = System.currentTimeMillis();

		TreeNode root = treeNodeRepository.findOne("#");
		logger.debug("begin build:[{}]", time);
		for (TreeNode child : root.getChildren()) {
			pool.submit(new CheckTask(invalidNodes, root, child)).join();
		}

		logger.debug("finish build cost:[{}], the root is:[{}]", System.currentTimeMillis() - time, root);
		logger.debug("[{}], invalid nodes is[]",
				invalidNodes.isEmpty() ? "机构树左右权值计算完成！验证无错误" : "机构树左右权值计算完成！有错误节点",
				invalidNodes);

		return invalidNodes;
	}

	private Comparator<TreeNode> comparator = new Comparator<TreeNode>() {

		@Override
		public int compare(TreeNode o1, TreeNode o2) {
			if (o1.getLeftPriority() == o2.getLeftPriority()) {
				return 0;
			}
			return o1.getLeftPriority() > o2.getLeftPriority() ? 1 : -1;
		}
	};

	private class CheckTask implements Runnable {

		private static final long serialVersionUID = -1604798008383512547L;

		public CheckTask(List<TreeNode> invalidNodes, TreeNode parent, TreeNode node) {
			this.invalidNodes = invalidNodes;
			this.parent = parent;
			this.node = node;
		}

		private List<TreeNode> invalidNodes;

		private TreeNode parent;

		private TreeNode node;

		@Override
		public void run() {
			if (parent == null) {
				return;
			}

			/* 父节点的左权值应比当前节点的小,否则认为当前节点权值不正确 */
			if (parent.getLeftPriority() > node.getLeftPriority()) {
				logger.warn("the node:[{}] left:[{}] < parent_left:[{}]", node.getId(),
						node.getLeftPriority(),
						node.getLeftPriority());
				invalidNodes.add(node);
				return;
			}

			/* 父节点的右权值应比当前节点的大,否则认为当前节点权值不正确 */
			if (parent.getRightPriority() < node.getRightPriority()) {
				logger.warn("the node:[{}] right:[{}] > parent_right:[{}]", node.getId(),
						node.getRightPriority(),
						parent.getRightPriority());
				invalidNodes.add(node);
				return;
			}

			if (node.getChildren().isEmpty()) {
				return;
			}

			/* 将所有子节点安装左权值排序(正序) */
			TreeSet<TreeNode> children = new TreeSet<>(comparator);
			children.addAll(node.getChildren());

			/* 节点的左权值==第一个子节点的左权值-1 */
			if (node.getLeftPriority() != (children.first().getLeftPriority() - 1)) {
				logger.warn("the node:[{}] left:[{}] != first_child_left:[{}-1]",
						node.getId(),
						node.getLeftPriority(),
						children.first().getLeftPriority());

				invalidNodes.add(node);
				return;
			}

			/* 子节点的右权值==最后一个子节点的右权值+1 */
			if (node.getRightPriority() != (children.last().getRightPriority() + 1)) {

				logger.warn("the node:[{}] right:[{}] != last_child_right:[{}+1]",
						node.getId(),
						node.getLeftPriority(),
						children.last().getLeftPriority());

				invalidNodes.add(node);
				return;
			}


			/* 启动迭代任务检查子节点 */
			List<ForkJoinTask<?>> tasks = new ArrayList<>();

			for (TreeNode child : children) {
				ForkJoinTask<?> task = pool.submit(new CheckTask(invalidNodes, node, child));

			}

			for (ForkJoinTask task : tasks) {
				task.join();
			}
		}

	}
}
