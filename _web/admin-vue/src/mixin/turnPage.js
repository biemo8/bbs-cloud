const turnPage = {
  methods: {
    //获取表格数据源 其中http代表api接口类, data代表请求接口参数，path代表请求路径
    getTableListData(http, data, path){
      return new Promise(async (resolve, reject) => {
        let tableListData = await http[path](data)
        let dataList = tableListData.data.records
        let pageNo = tableListData.data.current
        let pageSize = tableListData.data.size
        let total = tableListData.data.total
        resolve({dataList, pageNo, pageSize, total})
      })
    },
  }
}
export default turnPage
