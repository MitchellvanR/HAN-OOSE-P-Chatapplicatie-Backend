// import Vue from "vue"
// import Vuetify from "vuetify";
import {createLocalVue, mount, shallowMount} from '@vue/test-utils';
import chat from '@/views/chat';
// import sinon from 'sinon';

// const localVue = createLocalVue()
// Vue.use(Vuetify)

describe('chat.vue', () => {
    it('is called OpenChat', () => {
        const wrapper = shallowMount(chat);
        expect(wrapper.name()).toEqual('OpenChat');
    })
})