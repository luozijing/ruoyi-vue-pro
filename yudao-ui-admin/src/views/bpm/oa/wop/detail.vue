<template>
  <div class="app-container">
    <!-- 对话框(添加 / 修改) -->
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="申请人的用户编号：" prop="reason"> {{ form.userId }}</el-form-item>
        <el-form-item label="加班项目：" prop="type">
          <dict-tag :type="DICT_TYPE.BPM_OA_OWP_TYPE" :value="form.type"/>
        </el-form-item>
        <el-form-item label="申请人的用户编号：" prop="reason"> {{ form.userId }}</el-form-item>
        <el-form-item label="开始时间：" prop="startTime"> {{parseTime(form.startTime, '{y}-{m}-{d}')}} </el-form-item>
        <el-form-item label="结束时间：" prop="endTime"> {{parseTime(form.endTime, '{y}-{m}-{d}')}} </el-form-item>
        <el-form-item label="加班结果：" prop="reason"> <dict-tag :type="DICT_TYPE.BPM_PROCESS_INSTANCE_RESULT" :value="form.result"/></el-form-item>
        <el-form-item label="加班原因：" prop="result"> {{ form.reason }}</el-form-item>
      </el-form>
  </div>
</template>

<script>
import { getLeave}  from "@/api/bpm/leave"
import {getDictDatas, DICT_TYPE} from '@/utils/dict'
import {getOaWop} from "@/api/bpm/oaWop";
export default {
  name: "LeaveDetail",
  components: {
  },
  data() {
    return {
      id: undefined, // 请假编号
      // 表单参数
      form: {
        userId: undefined,
        startTime: undefined,
        endTime: undefined,
        type: undefined,
        reason: undefined,
        result: undefined,
      },

      typeDictData: getDictDatas(DICT_TYPE.BPM_OA_LEAVE_TYPE),
    };
  },
  created() {
    this.id = this.$route.query.id;
    if (!this.id) {
      this.$message.error('未传递 id 参数，无法查看OA请假信息');
      return;
    }
    this.getDetail();
  },
  methods: {
    /** 获得请假信息 */
    getDetail() {
      getOaWop(this.id).then(response => {
        this.form = response.data;
      });
    },
  }
};
</script>
