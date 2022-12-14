import { mount } from '@vue/test-utils';
import menu from '@/views/menu';

describe('menu.vue', () => {
    // De stubs: ['router-link'] is nodig omdat er anders warnings worden gegeven dat de router-link niet herkend wordt!
    const wrapper = mount(menu, {
        stubs: ['router-link']
    });

    it('check if component exist', () => {
        expect(wrapper.findComponent(menu).exists()).toBe(true);
    })

    it('check if button user1 is clicked', () => {
        wrapper.vm.navigate = jest.fn();
        const button = wrapper.find('#user1');
        button.trigger('click');
        expect(wrapper.vm.navigate).toHaveBeenCalled();
    })
})