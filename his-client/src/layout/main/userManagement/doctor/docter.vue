<template>
    <a-card>
        <a-form
                ref="formRef"
                :model="model"
                :rules="rules"
                :label-col="labelCol"
                :wrapper-col="{wrapperCol}"
        >
            <a-row>
                <a-col :span="6">
                    <a-form-item label="姓名" name="name">
                        <a-input v-model:value="model.name" placeholder="请输入姓名"/>
                    </a-form-item>
                </a-col>
                <a-col :span="6">
                    <a-form-item label="员工编号" name="employeeNumber">
                        <a-input v-model:value="model.employeeNumber" placeholder="请输入员工编号"/>
                    </a-form-item>
                </a-col>
                <a-col :span="6">
                    <a-button type="primary" @click="handleSubmit" style="margin:0 10px 0 10px">查询</a-button>
                    <a-button type="primary" @click="handleReset">重置</a-button>
                </a-col>
            </a-row>
        </a-form>
        <a-table
                :data-source="dataSource"
                :columns="columns"
                :row-key="r=>r.id"
                :pagination="pagination"
                :loading="loading"
                @change="handleTableChange"
        >

        </a-table>
    </a-card>
</template>

<script setup lang="ts">
import {getDoctorListApi} from '@/api/user/doctor.ts'
import {reactive, ref, UnwrapRef} from "vue";
import {Rule} from "ant-design-vue/es/form";
import {TableProps} from "ant-design-vue";

interface Model {
    name: string,
    userType: string,
    employeeNumber: string,
}

const model: UnwrapRef<Model> = reactive({
    name: '',
    userType: 'doctor',
    employeeNumber: '',
})
const labelCol = {span: 5}
const wrapperCol = {span: 13}
const rules: Record<string, Rule[]> = {
    // name: [
    //     { required: true, message: '请输入姓名', trigger: 'change' },
    // ],
}

const handleSubmit = () => {
    loadData()
}
const handleReset = () => {
}

// table-begin
const dataSource = ref([])
const columns = [
    {
        title: '用户名',
        dataIndex: 'username',
    },
    {
        title: '医生名',
        dataIndex: 'employeeName',
    },
    {
        title: '员工编号',
        dataIndex: 'employeeNumber',
    },
    {
        title: '所在院区',
        dataIndex: 'campusName',
    },
    {
        title: '员工类型',
        dataIndex: 'userType',
        slots: {
            customRender: (text: string) => {
                return text === 'doctor' ? '医生' : '护士'
            },
        },
    },
]
const pagination = reactive({
    current: 1,
    pageSize: 5,
    pageSizeOptions: ['5', '10', '15'],
    total: 0,
    showSizeChanger: true,
    showTotal: (total: number) => `共 ${total} 条`,
})

const loading = ref(false)
const loadData = (current=1,pageSize=5) => {
    loading.value = true

    let param = {...model}
    param['current']= current
    param['size'] = pageSize

    getDoctorListApi(param).then((res: any) => {
        if (res.status === 200) {
            console.log('res.data.records',res.data.records)
            dataSource.value = res.data.records
        }
    }).finally(() => {
        loading.value = false
    })
}

const handleTableChange: TableProps['onChange'] = (
    pag: { pageSize: number; current: number },
) => {
  loadData(pag.current,pag.pageSize)
};

// table-end


</script>
  <style scoped>
  
  </style>