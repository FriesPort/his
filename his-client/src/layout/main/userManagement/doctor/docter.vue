<template>
    <div>
      <div class="top">
        <el-form :inline="true" :model="form" label-width="100px">
          <el-form-item label="选择院区">
            <el-select v-model="form.area" placeholder="请选择院区" style="width: 110px">
              <el-option
                  v-for="item in areas"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-input v-model="form.name" placeholder="请输入姓名"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" circle size="small"></el-button>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" size="small" @click="add">
              新增用户
            </el-button>
          </el-form-item>
        </el-form>
      </div>
  
      <el-table
          :data="tableData"
          border
          :header-cell-style="{background:'#fafafa', color:'rgba(0, 0, 0, 0.88)',fontWeight: 600}">
        <el-table-column
            prop="name"
            label="姓名"
            width="220px">
        </el-table-column>
        <el-table-column
            prop="username"
            label="用户名"
            width="320px">
        </el-table-column>
        <el-table-column
            prop="employeeNumber"
            label="员工号"
            width="320px">
        </el-table-column>
        <el-table-column
            prop="area"
            label="所属院区"
            width="300px">
        </el-table-column>
        <el-table-column
            prop="phone"
            label="电话号码"
            width="220px">
        </el-table-column>
        <el-table-column
            width="300px"
            fixed="right"
            label="用户操作">
          <template slot-scope="scope">
            <el-button type="primary" icon="el-icon-edit" @click="update(scope.row)" size="medium"
                       style="border-radius: 6px">编辑
            </el-button>
            <el-button type="text" size="medium" @click="deleted(scope.row)" style="margin-left: 20px">
              <el-popconfirm
                  confirm-button-text='是'
                  cancel-button-text='否'
                  icon="el-icon-info"
                  title="是否确定删除此用户？"
              >
                   <span style="color: black" slot="reference">
                <i class="el-icon-delete"></i>
                删除
              </span>
              </el-popconfirm>
  
            </el-button>
            <el-button type="text" size="medium" round @click="addRole">
              角色分配
            </el-button>
          </template>
        </el-table-column>
      </el-table>
  
      <!--    管理编辑弹窗-->
      <el-dialog :title="title" :visible.sync="dialogVisible" width="30%" style="border-radius: 20px;">
        <el-form :model="ruleForm" ref="ruleForm" label-width="90px" class="demo-ruleForm">
          <el-form-item label="用户名:" prop="username">
            <el-input v-model="ruleForm.username" placeholder="请输入用户名" style="width: 150px;"></el-input>
          </el-form-item>
          <el-form-item label="密码:" prop="password" v-if="title === '新增医生'">
            <el-input v-model="ruleForm.password" placeholder="请输入密码" style="width: 150px;"></el-input>
          </el-form-item>
          <el-form-item label="姓名:" prop="name">
            <el-input v-model="ruleForm.name" placeholder="请输入姓名" style="width: 150px;"></el-input>
          </el-form-item>
          <el-form-item label="员工号:" prop="employeeNumber">
            <el-input v-model="ruleForm.employeeNumber" placeholder="请输入员工号" style="width: 150px;"></el-input>
          </el-form-item>
          <el-form-item label="手机号" prop="phone">
            <el-input v-model="ruleForm.phone" placeholder="请输入手机号" style="width: 150px;"></el-input>
          </el-form-item>
          <el-form-item label="所属院区" prop="area">
            <el-select v-model="ruleForm.area" placeholder="请选择院区" style="width: 150px">
              <el-option
                  v-for="item in areas"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="是否可用" prop="isUse">
            <el-select v-model="ruleForm.isUse" placeholder="请选择院区" style="width: 150px">
              <el-option
                  v-for="item in isUse"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="用户类别" prop="userType">
            <el-select v-model="ruleForm.userType" placeholder="请选择院区" style="width: 150px">
              <el-option
                  v-for="item in userType"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <!--        确定取消-->
          <div style="position: absolute;right: 10px;bottom: 10px">
            <el-button @click="dialogVisible = false" size="small" style="border-radius: 6px">取消</el-button>
            <el-button type="primary" @click="submit" size="small" style="border-radius: 6px">
              {{ title === '新增医生' ? '新增' : '修改' }}
            </el-button>
          </div>
        </el-form>
      </el-dialog>
  
      <!--角色分配-->
      <el-dialog title="角色分配" :visible.sync="dialogVisible2" width="45%" style="border-radius: 20px;">
        <el-transfer
            style="text-align: left; display: inline-block;margin: 20px"
            v-model="value"
            :left-default-checked="[2, 3]"
            :right-default-checked="[1]"
            :titles="['3项未拥有权限', '2项已拥有权限']"
            :format="{
          noChecked: '${total}',
          hasChecked: '${checked}/${total}'
        }"
            :data="data">
        </el-transfer>
        <div style="position: absolute;right: 10px;bottom: 10px">
          <el-button type="primary" @click="submitRole" size="small" style="border-radius: 6px">
            确认
          </el-button>
  
        </div>
      </el-dialog>
    </div>
  </template>
  
  <script>
  import axios from "axios";
  import {getDocterListApi} from  '@/api/user/doctor.ts'
  import { onBeforeMount} from "vue";
  
  export default {
    components: {},
    data() {
      return {
        name: '',
        employeeNumber: '',
        userType: '',
        current: 1,
        size: 5,
        value: ["5","4"],
        data: [
          {
            key: '0',
            label: "床位管理员"
          },
          {
            key: '1',
            label: "超级管理员"
          },
          {
            key: '2',
            label: "系统管理员"
          },
          {
            key: '4',
            label: "患床管理员"
          }, {
            key: '5',
            label: "床位调度员"
          }
  
        ],
        title: "",
        dialogVisible: false,  //修改新增弹窗
        dialogVisible2: false, //权限弹窗
        tableData: [{
          username: "admin",
          name: "张三",
          employeeNumber:"abcd",
          phone: '13784444444',
          isUse: "0",
          userType: "0",
          area: "第一院区"
        }, {
          username: "admin",
          name: "李四",
          employeeNumber:"abcd",
          phone: '13784444444',
          isUse: "0",
          userType: "0",
          area: "第一院区"
        }, {
          username: "admin",
          name: "王五",
          employeeNumber:"abcd",
          phone: '13784444444',
          isUse: "0",
          userType: "0",
          area: "第一院区"
        }],
        input: null,
        ruleForm: {},
        form: {
          area: '0', //默认全部
        },
        areas: [
          {
            value: '0',
            label: '全部'
          }, {
            value: '1',
            label: '广州院区'
          }, {
            value: '2',
            label: '深圳院区'
          }, {
            value: '3',
            label: '珠海院区'
          }
        ],
        isUse:[
            {
              value: '0',
              label: '是'
            }, {
              value: '1',
              label: '否'
            }
        ],
        userType:[
          {
            value: '0',
            label: '医生'
          }, {
            value: '1',
            label: '护士'
          }
        ]
      }
    },
    onBeforeMount() {
      this.getList()
      // axios.get('http://8.138.173.253/system/docter/query',{
      //   params:{
      //     name:'张三'
      //   }
      // }).then(res=>{
      //   this.tableData=res.data
      //   console.log(res)
      // })
    },
    methods: {
      async getList(){
        let params = {
            name: this.ruleForm.name,
            namemployeeNumbere: this.ruleForm.employeeNumber,
            userType: this.userType.name,
            current: this.current.name,
            size: this.ruleForm.size,
        }
        let result = await getDocterListApi(params);
        console.log(result);
        this.tableData = result.data.records
      },
      handleClick(row) {
        console.log(row)
      },
      // 角色分配
      addRole() {
        this.dialogVisible2 = true
      },
      add() {
        this.title = "新增医生"
        this.dialogVisible = true
        this.ruleForm = {}
        this.ruleForm.area = "1" //默认广州
        this.ruleForm.isUse = '0' //默认可用
      },
      update(row) {
        this.title = "修改医生"
        this.dialogVisible = true
        this.ruleForm = row
        this.$message.warning("未对接接口")
        //对接接口
      },
      submit() {
        this.dialogVisible = false
        this.$message.warning("未对接接口")
        //对接接口
      },
      submitRole() {
        this.dialogVisible2 = false
        this.$message.warning("未对接接口")
        //对接接口
      },
      deleted() {
        //对接接口
        this.$message.warning("未对接接口")
        //小弹窗
  
      }
    }
  }
  
  </script>
  <style scoped>
  
  </style>