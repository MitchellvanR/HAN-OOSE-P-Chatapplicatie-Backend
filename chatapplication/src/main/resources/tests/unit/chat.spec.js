import Vue from "vue"
import Vuetify from "vuetify";
import {createLocalVue, mount, shallowMount} from '@vue/test-utils';
import chat from '@/views/chat';
// import sinon from 'sinon';

const localVue = createLocalVue()
Vue.use(Vuetify)

describe('chat.vue', () => {
    it('is called OpenChat', () => {
        // de functie getChatLog moet gemocked worden om de test te laten slagen, anders wordt de mounted elke keer uitgevoerd
        let getChatLog = jest.fn();
        const wrapper = shallowMount(chat, {
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
    })
})