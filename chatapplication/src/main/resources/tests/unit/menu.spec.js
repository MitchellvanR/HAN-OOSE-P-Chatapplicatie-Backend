import Vue from "vue"
import Vuetify from "vuetify";
import {createLocalVue, mount} from '@vue/test-utils';
import menu from '@/views/menu';

const localVue = createLocalVue()
Vue.use(Vuetify)

describe('menu.vue', () => {
    let vuetify;
    beforeEach(() => {
        vuetify = new Vuetify();
    });

    // De stubs: ['router-link'] is nodig omdat er anders warnings worden gegeven dat de router-link niet herkend wordt!
    const wrapper = mount(menu, {
        localVue,
        vuetify,
        stubs: ['router-link']
    });

    it('check if component exist', () => {
        expect(wrapper).toBeTruthy();
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
});