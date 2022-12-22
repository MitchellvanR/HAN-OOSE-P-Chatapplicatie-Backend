import Vue from "vue"
import Vuetify from "vuetify";
import {createLocalVue, mount, shallowMount} from '@vue/test-utils';
import menu from '@/views/menu';
import sinon from 'sinon';

const localVue = createLocalVue()
Vue.use(Vuetify)

describe('menu.vue', () => {
    let vuetify;
    beforeEach(() => {
        vuetify = new Vuetify();
    });

    // De stubs: ['router-link'] is nodig omdat er anders warnings worden gegeven dat de router-link niet herkend wordt!
    let wrapper = mount(menu, {
        localVue,
        vuetify,
        stubs: ['router-link'],
    });

    it('Is called UserMenu', () => {
        const wrapper = shallowMount(menu);
        expect(wrapper.name()).toEqual('UserMenu');
    });

    it('check if setUserId is called after click button user 1', async () => {
        const listener = sinon.spy();
        const wrapper = shallowMount(menu);
        wrapper.setMethods({
            setUserId: listener
        })

        wrapper.find('#user1').trigger('click');
        expect(listener.called);
    });

    it('check if setUserId is called after click button user 2', async () => {
        const listener = sinon.spy();
        const wrapper = shallowMount(menu);
        wrapper.setMethods({
            setUserId: listener
        })

        wrapper.find('#user2').trigger('click');
        expect(listener.called);
    });

    it('check if setUserId is called after click button administrator', async () => {
        const listener = sinon.spy();
        const wrapper = shallowMount(menu);
        wrapper.setMethods({
            setUserId: listener
        })

        wrapper.find('#administrator').trigger('click');
        expect(listener.called);
    });
});