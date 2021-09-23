<template>
  <div>
    <el-upload
      ref="upload"
      class="avatar-uploader"
      action="/file/upload"
      :headers="config"
      name="file"
      :show-file-list="false"
      :disabled="isDetail"
      :on-success="handleAvatarSuccess"
      :on-progress="handleLoading"
      :on-error="handleError"
      :before-upload="beforeAvatarUpload">
      <el-image v-show="imageUrl" :src="imageUrl" fit="cover" class="avatar"></el-image>
      <i v-show="!imageUrl" class="el-icon-plus avatar-uploader-icon" style="border: 1px dashed #7f7f7f"></i>
    </el-upload>
  </div>
</template>

<script>
  import StorageHandler from "@/utils/StorageHandler.js"
  export default {
    name: "img-upload",
    data(){
      return{
        storageHandler: new StorageHandler(),
        loading:false,
        imageUrl:this.value,
        uploadPercent:0,
      }
    },
    props:{
      value:{
        type:String
      },
      isDetail:{
        type:Boolean
      },
    },
    computed: {
      config: function () {
        return {"Authorization": this.storageHandler.getSessionStorage('token')}
      },
    },
    watch:{
      value(curVal,oldVal){
        this.imageUrl = curVal
      },
    },
    methods:{
      //上传成功后
      handleAvatarSuccess(res, file) {
        this.loading = false
        this.imageUrl = res.data.fileUrl
        this.$emit('input', res.data.fileUrl)
      },
      //上传之前
      beforeAvatarUpload(file) {
        const isJPG = file.type === 'image/jpeg' || file.type === 'image/jpg' || file.type === 'image/png';
        if (!isJPG) {
          this.$message.error('上传的图片只能是 JPG/JPEG/PNG 格式!');
        }
        return isJPG;
      },
      //上传过程中
      handleLoading(event, file, fileList){
        this.loading = true
        this.uploadPercent = Number(file.percentage.toFixed(0))
      },
      //上传失败
      handleError(file, fileList) {
        this.loading = false
        this.$message.error('上传失败！')
      },
    },
  }
</script>

<style scoped>
  .avatar-uploader .el-upload {
    border: 1px solid #7f7f7f;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
    text-align: center !important;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 100px;
    height: 100px;
    line-height: 100px;
    text-align: center;
  }
  .avatar {
    width: 100px;
    height: 100px;
    display: block;
  }
</style>
