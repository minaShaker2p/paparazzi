package app.cash.paparazzi.internal

import com.android.ide.common.rendering.api.ResourceNamespace
import com.android.ide.common.resources.AbstractResourceRepository
import com.android.ide.common.resources.ResourceItem
import com.android.ide.common.resources.ResourceTable
import com.android.ide.common.resources.ResourceVisitor
import com.android.ide.common.resources.SingleNamespaceResourceRepository
import com.android.resources.ResourceType
import com.google.common.collect.ImmutableListMultimap
import com.google.common.collect.ListMultimap

class PaparazziResourceRepository(
    private val resourceNamespace: ResourceNamespace,
) : AbstractResourceRepository(), SingleNamespaceResourceRepository {

  private val resourceTable = ResourceTable()

  override fun getNamespace(): ResourceNamespace = resourceNamespace

  override fun getPackageName(): String = resourceNamespace.packageName

  override fun getResourcesInternal(
      namespace: ResourceNamespace, resourceType: ResourceType): ListMultimap<String, ResourceItem> {
    if (namespace != namespace) {
      return ImmutableListMultimap.of()
    }
    return resourceTable.get(namespace, resourceType) ?: ImmutableListMultimap.of()
  }

  override fun accept(visitor: ResourceVisitor): ResourceVisitor.VisitResult {
    if (visitor.shouldVisitNamespace(namespace)) {
      for ((_, value) in resourceTable.rowMap().entries) {
        if (acceptByResources(value, visitor) == ResourceVisitor.VisitResult.ABORT) {
          return ResourceVisitor.VisitResult.ABORT
        }
      }
    }
    return ResourceVisitor.VisitResult.CONTINUE
  }

  override fun getPublicResources(
      namespace: ResourceNamespace, type: ResourceType): Collection<ResourceItem?> {
    throw UnsupportedOperationException()
  }
}