<template>
    <a-modal
        :visible="props.visible"
        destroyOnClose="true"
        :width="props.width"
        :title="props.title"
        @cancel="onClose"
        wrapClassName="custom-modal"
    >
        <template #footer>
            <a-button type="danger" @click="onClose">取消</a-button>
            <a-button type="primary" @click="onConfirm">确定</a-button>
        </template>
        <!-- 弹框内容 -->
        <div :style="{ height: props.height + 'px' }" style="overflow-y: auto;overflow-x: initial;">
            <slot name="content"></slot>
        </div>
    </a-modal>
</template>
<script setup lang='ts'>
//子组件接收父组件的值 defineProps
const props = withDefaults(defineProps<{
    title?: string;
    visible?: boolean;
    width: number;
    height: number;
}>(), {
    title: '标题',
    visible: false,
    width: 650,
    height: 250
})

//注册事件
const emit = defineEmits(['onClose', 'onConfirm'])

//关闭弹框的事件
const onClose = () => {
    emit('onClose')
}

//弹框确定事件
const onConfirm = () => {
    emit('onConfirm')
}
</script>
<style lang='scss'>
:deep(.ant-modal-header) {
    padding: 14px 24px !important;
    border-top-left-radius: 7px !important;
    border-top-right-radius: 7px !important;
    background: #1890ff !important;
    .ant-modal-title {
        color: #fff !important;
    }
}
.custom-modal .ant-modal-content {
    border-top-left-radius: 7px !important;
    border-top-right-radius: 7px !important;
    .ant-modal-header {
        padding: 14px 24px !important;
        border-top-left-radius: 7px !important;
        border-top-right-radius: 7px !important;
        background: #1890ff !important;
        .ant-modal-title {
            color: #fff !important;
        }
    }
    .ant-modal-body {
        padding: 10px 10px !important;
    }
    .ant-modal-close {
        .ant-modal-close-icon {
            color: #fff !important;
        }
    }
}
</style>