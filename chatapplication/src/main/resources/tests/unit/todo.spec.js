// Dit is mijn voorbeeld voor het uitwerken van de unit tests ~Jesse

// import { mount } from 'vue-test-utils'
// import { expect } from 'chai'
// import Todo from '@/components/ToDo'
// import sinon from "sinon";

// describe('Todo.vue', () => {
//     it('Is called todo', () => {
//         const wrapper = mount(Todo);
//
//         // Assert result
//         expect(wrapper.name()).to.equal('ToDo');
//     });
//
//     it('Emits the remove event', () => {
//         const listener = sinon.spy();
//         const wrapper = mount(Todo);
//         wrapper.setMethods({
//             $emit: listener,
//         });
//
//         wrapper.find('button').trigger('click');
//
//         // Assert result
//         expect(listener.calledWith('remove'));
//     });
//
//     it('Emits the toggle event', () => {
//         const listener = sinon.spy();
//         const wrapper = mount(Todo);
//         wrapper.setMethods({
//             $emit: listener,
//         });
//
//         wrapper.trigger('click');
//
//         expect(listener.calledWith('toggle'));
//     });
//
//     it('Calls the remove method', () => {
//         const listener = sinon.spy();
//         const wrapper = mount(Todo);
//         wrapper.setMethods({
//             remove: listener,
//         });
//
//         wrapper.find('button').trigger('click');
//
//         expect(listener.called);
//     });
//
//     it('Calls the toggle method', () => {
//         const listener = sinon.spy();
//         const wrapper = mount(Todo);
//         wrapper.setMethods({
//             toggle: listener,
//         });
//
//         wrapper.trigger('click');
//
//         expect(listener.called);
//     });
//
//     it('Applies the disabled class', () => {
//         const wrapper = mount(Todo, {
//             propsData: {
//                 isFinished: true,
//             },
//         });
//
//         expect(wrapper.hasClass('disabled'));
//     });
//
//     it('Applies the content from a property', () => {
//         const content = 'Hello World';
//         const wrapper = mount(Todo, {
//             propsData: {
//                 content
//             },
//         });
//
//         expect(wrapper.text().contains(content));
//     });
//
//     it('Applies the content from a slot', () => {
//         const content = 'Hello World';
//         const wrapper = mount(Todo, {
//             slots: {
//                 default: '<div>${content}</div>'
//             }
//         });
//
//         expect(wrapper.text()).contains(content);
//     });
// });