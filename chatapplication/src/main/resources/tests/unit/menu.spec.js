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

    it("check if button user 1 is clicked", () => {
        const event = jest.fn();
        const button = wrapper.find('#user1');

        button.vm.$on("click", event);
        expect(event).toHaveBeenCalledTimes(0);
        button.trigger("click");
        expect(event).toHaveBeenCalledTimes(1);
    });

    it("check if button user 2 is clicked", () => {
        const event = jest.fn();
        const button = wrapper.find('#user2');

        button.vm.$on("click", event);
        expect(event).toHaveBeenCalledTimes(0);
        button.trigger("click");
        expect(event).toHaveBeenCalledTimes(1);
    });

    it("check if button administrator is clicked", () => {
        const event = jest.fn();
        const button = wrapper.find('#administrator');

        button.vm.$on("click", event);
        expect(event).toHaveBeenCalledTimes(0);
        button.trigger("click");
        expect(event).toHaveBeenCalledTimes(1);
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