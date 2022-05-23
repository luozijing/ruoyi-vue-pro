<template>
  <div class="app-container">

    <!-- 搜索工作栏 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="申请人编号" prop="userId">
        <el-input v-model="queryParams.userId" placeholder="请输入申请人的用户编号" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="加班项目" prop="type">
        <el-select v-model="queryParams.type" placeholder="请选择加班项目" clearable size="small">
          <el-option v-for="dict in owpTypeDictData" :key="dict.value" :label="dict.label" :value="dict.value" />
        </el-select>
      </el-form-item>
      <el-form-item label="加班原因" prop="reason">
        <el-input v-model="queryParams.reason" placeholder="请输入加班原因" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="加班开始时间">
        <el-date-picker v-model="dateRangeStartTime" style="width: 240px" value-format="yyyy-MM-dd"
                        type="daterange" range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期" />
      </el-form-item>
      <el-form-item label="加班结束时间">
        <el-date-picker v-model="dateRangeEndTime" style="width: 240px" value-format="yyyy-MM-dd"
                        type="daterange" range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期" />
      </el-form-item>
      <el-form-item label="加班天数" prop="day">
        <el-input v-model="queryParams.day" placeholder="请输入加班天数" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="结果" prop="result">
        <el-select v-model="queryParams.result" placeholder="请选择流结果" clearable>
          <el-option v-for="dict in this.getDictDatas(DICT_TYPE.BPM_PROCESS_INSTANCE_RESULT)"
                     :key="dict.value" :label="dict.label" :value="dict.value"/>
        </el-select>
      </el-form-item>
      <el-form-item label="流程实例编号" prop="processInstanceId">
        <el-input v-model="queryParams.processInstanceId" placeholder="请输入流程实例的编号" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="创建时间">
        <el-date-picker v-model="dateRangeCreateTime" style="width: 240px" value-format="yyyy-MM-dd"
                        type="daterange" range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <!-- 操作工具栏 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
                   v-hasPermi="['bpm:oa-wop:create']">发起申请</el-button>
      </el-col>
      <!--<el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport" :loading="exportLoading"
                   v-hasPermi="['bpm:oa-wop:export']">导出</el-button>
      </el-col>-->
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <!-- 列表 -->
    <el-table v-loading="loading" :data="list">
      <el-table-column label="申请编号" align="center" prop="id" />
      <el-table-column label="申请人ID" align="center" prop="userId" />
      <el-table-column label="加班项目" align="center" prop="type" />
      <el-table-column label="加班原因" align="center" prop="reason" />
      <el-table-column label="加班开始时间" align="center" prop="startTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.startTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="加班结束时间" align="center" prop="endTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.endTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="加班天数" align="center" prop="day" />
      <el-table-column label="申请结果" align="center" prop="result" />
      <el-table-column label="流程实例编号" align="center" prop="processInstanceId" />
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleCancel(scope.row)"
                     v-hasPermi="['bpm:oa-wop:delete']" v-if="scope.row.result === 1">取消申请</el-button>
          <el-button size="mini" type="text" icon="el-icon-view" @click="handleDetail(scope.row)"
                     v-hasPermi="['bpm:oa-wop:query']">详情</el-button>
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleProcessDetail(scope.row)">审批进度</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页组件 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNo" :limit.sync="queryParams.pageSize"
                @pagination="getList"/>

    <!-- 对话框(添加 / 修改) -->
    <!--<el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="申请人的用户编号" prop="userId">
          <el-input v-model="form.userId" placeholder="请输入申请人的用户编号" />
        </el-form-item>
        <el-form-item label="请假项目" prop="type">
          <el-select v-model="form.type" placeholder="请选择请假项目">
            <el-option label="请选择字典生成" value="" />
          </el-select>
        </el-form-item>
        <el-form-item label="加班原因" prop="reason">
          <el-input v-model="form.reason" placeholder="请输入加班原因" />
        </el-form-item>
        <el-form-item label="加班开始时间" prop="startTime">
          <el-date-picker clearable v-model="form.startTime" type="date" value-format="yyyy-MM-dd" placeholder="选择加班开始时间" />
        </el-form-item>
        <el-form-item label="加班结束时间" prop="endTime">
          <el-date-picker clearable v-model="form.endTime" type="date" value-format="yyyy-MM-dd" placeholder="选择加班结束时间" />
        </el-form-item>
        <el-form-item label="加班天数" prop="day">
          <el-input v-model="form.day" placeholder="请输入加班天数" />
        </el-form-item>
        <el-form-item label="申请结果" prop="result">
          <el-input v-model="form.result" placeholder="请输入申请结果" />
        </el-form-item>
        <el-form-item label="流程实例的编号" prop="processInstanceId">
          <el-input v-model="form.processInstanceId" placeholder="请输入流程实例的编号" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>-->
  </div>
</template>

<script>
import { createOaWop, updateOaWop, deleteOaWop, getOaWop, getOaWopPage, exportOaWopExcel } from "@/api/bpm/oaWop";
import {DICT_TYPE, getDictDatas} from "@/utils/dict";
import {cancelProcessInstance} from "@/api/bpm/processInstance";

export default {
  name: "OaWop",
  components: {
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 导出遮罩层
      exportLoading: false,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 申请加班工资业务列表
      list: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      dateRangeStartTime: [],
      dateRangeEndTime: [],
      dateRangeCreateTime: [],
      // 查询参数
      queryParams: {
        pageNo: 1,
        pageSize: 10,
        userId: null,
        type: null,
        reason: null,
        day: null,
        result: null,
        processInstanceId: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        userId: [{ required: true, message: "申请人的用户编号不能为空", trigger: "blur" }],
        type: [{ required: true, message: "请假项目不能为空", trigger: "change" }],
        reason: [{ required: true, message: "加班原因不能为空", trigger: "blur" }],
        startTime: [{ required: true, message: "加班开始时间不能为空", trigger: "blur" }],
        endTime: [{ required: true, message: "加班结束时间不能为空", trigger: "blur" }],
        day: [{ required: true, message: "加班天数不能为空", trigger: "blur" }],
        result: [{ required: true, message: "申请结果不能为空", trigger: "blur" }],
      },
      owpTypeDictData: getDictDatas(DICT_TYPE.BPM_OA_OWP_TYPE),
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询列表 */
    getList() {
      this.loading = true;
      // 处理查询参数
      let params = {...this.queryParams};
      this.addBeginAndEndTime(params, this.dateRangeStartTime, 'startTime');
      this.addBeginAndEndTime(params, this.dateRangeEndTime, 'endTime');
      this.addBeginAndEndTime(params, this.dateRangeCreateTime, 'createTime');
      // 执行查询
      getOaWopPage(params).then(response => {
        this.list = response.data.list;
        this.total = response.data.total;
        this.loading = false;
      });
    },
    /** 取消按钮 */
    cancel() {
      this.open = false;
      this.reset();
    },
    /** 表单重置 */
    reset() {
      this.form = {
        id: undefined,
        userId: undefined,
        type: undefined,
        reason: undefined,
        startTime: undefined,
        endTime: undefined,
        day: undefined,
        result: undefined,
        processInstanceId: undefined,
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNo = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.dateRangeStartTime = [];
      this.dateRangeEndTime = [];
      this.dateRangeCreateTime = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 取消申请操作 */
    handleCancel(row) {
      const id = row.processInstanceId;
      this.$prompt('请输入取消原因？', "取消流程", {
        type: 'warning',
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        inputPattern: /^[\s\S]*.*[^\s][\s\S]*$/, // 判断非空，且非空格
        inputErrorMessage: "取消原因不能为空",
      }).then(({ value }) => {
        return cancelProcessInstance(id, value);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("取消成功");
      })
    },
    /** 详情按钮操作 */
    handleDetail(row) {
      this.$router.push({ path: "/bpm/oa/wop/detail", query: { id: row.id}});
    },
    /** 查看审批进度的操作 */
    handleProcessDetail(row) {
      this.$router.push({ path: "/bpm/process-instance/detail", query: { id: row.processInstanceId}});
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.$router.push({ path: "/bpm/oa/wop/create"});
    }
  }
};
</script>
