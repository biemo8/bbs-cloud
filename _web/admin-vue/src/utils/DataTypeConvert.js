import _ from 'lodash'

//扁平数据转树形数据  list代表需要转换的数据，id代表每一条的id键值名称，parentId代表父级id键值名称, topNode为顶级节点的父id
export function flatTurnTree(list, id, parentId, topNode = -1) {
  let temp = {}
  let ans = []
  for (let i in list) {
    temp[list[i][id]] = list[i]
  }
  for (let i in temp) {
    if (temp[i][parentId] !== topNode && temp[i][id]) {
      if (!temp[temp[i][parentId]].children) {
        temp[temp[i][parentId]].children = new Array()
      }
      temp[temp[i][parentId]].children.push(temp[i])
    } else {
      ans.push(temp[i])
    }
  }
  if(ans.length !== 0 && !_.isArray(ans[0].children)){
    ans[0].children = []
  }
  return ans
}
