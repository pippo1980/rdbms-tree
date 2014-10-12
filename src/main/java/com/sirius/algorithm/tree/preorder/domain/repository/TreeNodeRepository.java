package com.sirius.algorithm.tree.preorder.domain.repository;

import com.sirius.algorithm.tree.preorder.domain.model.TreeNode;
import com.sirius.plugin.framework.jpa.domain.repository.BaseRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by pippo on 14-9-4.
 */
@Repository
public interface TreeNodeRepository extends BaseRepository<TreeNode> {

	List<TreeNode> findByParentId(String parentId, Sort sort);

}
