import request from '@/utils/request'

// 创建申请加班工资业务
export function createOaWop(data) {
  return request({
    url: '/bpm/oa-wop/create',
    method: 'post',
    data: data
  })
}

// 更新申请加班工资业务
export function updateOaWop(data) {
  return request({
    url: '/bpm/oa-wop/update',
    method: 'put',
    data: data
  })
}

// 删除申请加班工资业务
export function deleteOaWop(id) {
  return request({
    url: '/bpm/oa-wop/delete?id=' + id,
    method: 'delete'
  })
}

// 获得申请加班工资业务
export function getOaWop(id) {
  return request({
    url: '/bpm/oa-wop/get?id=' + id,
    method: 'get'
  })
}

// 获得申请加班工资业务分页
export function getOaWopPage(query) {
  return request({
    url: '/bpm/oa-wop/page',
    method: 'get',
    params: query
  })
}

// 导出申请加班工资业务 Excel
export function exportOaWopExcel(query) {
  return request({
    url: '/bpm/oa-wop/export-excel',
    method: 'get',
    params: query,
    responseType: 'blob'
  })
}
