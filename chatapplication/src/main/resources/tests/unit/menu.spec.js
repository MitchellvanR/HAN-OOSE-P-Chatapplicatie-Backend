import Vue from "vue"
import Vuetify from "vuetify";
import {shallowMount} from '@vue/test-utils';
import menu from '@/views/menu';
import sinon from 'sinon';

Vue.use(Vuetify)

describe('menu.vue', () => {
    let vuetify;
    let wrapper;
    const listener = sinon.spy();

    beforeEach(() => {
        vuetify = new Vuetify();
        wrapper = shallowMount(menu);
    });

    function setWrapperMethods() {
        wrapper.setMethods({
            setUserId: listener
        })
    }

    it('Is called UserMenu', () => {
        expect(wrapper.name()).toEqual('UserMenu');
    });

    it('check if setUserId is called after click button user 1', async () => {
        setWrapperMethods();

        wrapper.find('#user1').trigger('click');
        expect(listener.called);
    });

    it('check if setUserId is called after click button user 2', async () => {
        setWrapperMethods();

        wrapper.find('#user2').trigger('click');
        expect(listener.called);
    });

    it('check if setUserId is called after click button administrator', async () => {
        setWrapperMethods();

        wrapper.find('#administrator').trigger('click');
        expect(listener.called);
    });
});