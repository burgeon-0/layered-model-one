package org.burgeon.sbd.infra.repository;

import org.burgeon.sbd.infra.repository.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author Sam Lu
 * @date 2021/6/1
 */
public interface ProductEntityRepository extends PagingAndSortingRepository<ProductEntity, String> {

    /**
     * 根据ID查询产品
     *
     * @param productNo
     * @param deleted
     * @return
     */
    ProductEntity findByProductNoAndDeleted(String productNo, boolean deleted);

    /**
     * 批量查询产品
     *
     * @param pageable
     * @param deleted
     * @return
     */
    Page<ProductEntity> findAllByDeleted(Pageable pageable, boolean deleted);

}
