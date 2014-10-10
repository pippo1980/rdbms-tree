package com.sirius.algorithm.tree.preorder.domain.repository;

import com.sirius.algorithm.tree.preorder.domain.model.TreeNode;
import com.sirius.plugin.framework.jpa.domain.repository.BaseRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by pippo on 14-9-4.
 */
@Repository
public interface TreeNodeRepository extends BaseRepository<TreeNode> {

}
