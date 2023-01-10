import Vue from "vue"
import Vuetify from "vuetify";
import {createLocalVue, mount, shallowMount} from '@vue/test-utils';
import chat from '@/views/chat';
import sinon from "sinon";
// import sinon from 'sinon';

const localVue = createLocalVue()
Vue.use(Vuetify)

describe('chat.vue', () => {
    let wrapper;

    it('is called OpenChat', () => {
        // de functies uit de mounted() moeten gemocked worden, anders werkt de test niet
        let getChatLog = jest.fn();
        let savePublicKey = jest.fn();
        let delay = jest.fn();
        let addUserToCurrentChat = jest.fn();

        wrapper = shallowMount(chat, {
            methods: {
                getChatLog,
                savePublicKey,
                delay,
                addUserToCurrentChat
            },
            localVue
        })
        expect(wrapper.name()).toEqual('OpenChat');
    })

    // it('should called getChatLog when component is mounted', () => {
    //     let getChatLog = jest.fn();
    //     shallowMount(chat, {
    //         methods: {
    //             getChatLog
    //         },
    //         localVue
    //     })
    //     expect(getChatLog).toHaveBeenCalledTimes(1);
        // wrapper = shallowMount(chat);
        // const getChatLog = jest.spyOn(wrapper.vm., 'getChatLog');
        //
        // expect(getChatLog).toHaveBeenCalled();
    // })

    // it('iets', () => {
    //     wrapper = shallowMount(chat);
    //     let getChatLog = jest.spyOn(wrapper.vm.getChatLog, 'getChatLog');
    //     let runWebSocket = jest.spyOn(chat.methods, 'runWebSocket');
    //
    //     expect(getChatLog).toHaveBeenCalled();
    //     expect(runWebSocket).toHaveBeenCalled();
    // })
})