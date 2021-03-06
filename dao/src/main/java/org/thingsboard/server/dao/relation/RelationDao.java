package org.thingsboard.server.dao.relation;

import com.google.common.util.concurrent.ListenableFuture;
import org.thingsboard.server.common.data.EntityType;
import org.thingsboard.server.common.data.id.EntityId;
import org.thingsboard.server.common.data.id.TenantId;
import org.thingsboard.server.common.data.page.PageData;
import org.thingsboard.server.common.data.page.TimePageLink;
import org.thingsboard.server.common.data.relation.EntityRelation;
import org.thingsboard.server.common.data.relation.RelationTypeGroup;

import java.util.List;

/**
 * Created by ashvayka on 25.04.17.
 */
public interface RelationDao {

    ListenableFuture<List<EntityRelation>> findAllByFrom(TenantId tenantId, EntityId from, RelationTypeGroup typeGroup);

    ListenableFuture<List<EntityRelation>> findAllByFromAndType(TenantId tenantId, EntityId from, String relationType, RelationTypeGroup typeGroup);

    ListenableFuture<List<EntityRelation>> findAllByTo(TenantId tenantId, EntityId to, RelationTypeGroup typeGroup);

    ListenableFuture<List<EntityRelation>> findAllByToAndType(TenantId tenantId, EntityId to, String relationType, RelationTypeGroup typeGroup);

    ListenableFuture<Boolean> checkRelation(TenantId tenantId, EntityId from, EntityId to, String relationType, RelationTypeGroup typeGroup);

    ListenableFuture<EntityRelation> getRelation(TenantId tenantId, EntityId from, EntityId to, String relationType, RelationTypeGroup typeGroup);

    boolean saveRelation(TenantId tenantId, EntityRelation relation);

    ListenableFuture<Boolean> saveRelationAsync(TenantId tenantId, EntityRelation relation);

    boolean deleteRelation(TenantId tenantId, EntityRelation relation);

    ListenableFuture<Boolean> deleteRelationAsync(TenantId tenantId, EntityRelation relation);

    boolean deleteRelation(TenantId tenantId, EntityId from, EntityId to, String relationType, RelationTypeGroup typeGroup);

    ListenableFuture<Boolean> deleteRelationAsync(TenantId tenantId, EntityId from, EntityId to, String relationType, RelationTypeGroup typeGroup);

    boolean deleteOutboundRelations(TenantId tenantId, EntityId entity);

    ListenableFuture<Boolean> deleteOutboundRelationsAsync(TenantId tenantId, EntityId entity);

}
