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
    const listener = sinon.spy();

    it('is called OpenChat', () => {
        // de functie getChatLog moet gemocked worden om de test te laten slagen, anders wordt de mounted elke keer uitgevoerd
        let getChatLog = jest.fn();
        wrapper = shallowMount(chat, {
            methods: {
                getChatLog
            },
            localVue
        })
        expect(wrapper.name()).toEqual('OpenChat');
    })

    it('should called getChatLog when component is mounted', () => {
        let getChatLog = jest.fn();
        shallowMount(chat, {
            methods: {
                getChatLog
            },
            localVue
        })
        expect(getChatLog).toHaveBeenCalledTimes(1);
        // wrapper = shallowMount(chat);
        // const getChatLog = jest.spyOn(wrapper.vm., 'getChatLog');
        //
        // expect(getChatLog).toHaveBeenCalled();
    })

    it('iets', () => {
        wrapper = shallowMount(chat);
        let getChatLog = jest.spyOn(wrapper.vm.getChatLog, 'getChatLog');
        let runWebSocket = jest.spyOn(chat.methods, 'runWebSocket');

        expect(getChatLog).toHaveBeenCalled();
        expect(runWebSocket).toHaveBeenCalled();
    })
})