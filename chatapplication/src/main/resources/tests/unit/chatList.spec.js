import {createLocalVue, shallowMount} from "@vue/test-utils";
import chatList from "@/views/chatList";
import sinon from "sinon";

const localVue = createLocalVue();

describe('chatList.vue', () => {
    let wrapper;
    const listener = sinon.spy();
    let setHelplineChat = jest.fn();
    let savePublicKey = jest.fn();
    let createChat = jest.fn();
    let getAnnouncements = jest.fn();
    let getAllChatsFromUser = jest.fn();

    function setWrapper() {
        wrapper = shallowMount(chatList, {
            methods: {
                setHelplineChat,
                savePublicKey,
                createChat,
                getAnnouncements,
                getAllChatsFromUser
            },
            localVue
        });
    }

    it('is called ChatList', () => {
        setWrapper();
        expect(wrapper.name()).toEqual('ChatList');
    });

    it('calls setHelplineChat on mount',  () => {
        setWrapper();
        expect(setHelplineChat).toBeCalled();
    });

    it('calls savePublicKey on mount',  () => {
        setWrapper();
        expect(savePublicKey).toBeCalled();
    });

    it('calls createChat on mount',  () => {
        setWrapper();
        expect(createChat).toBeCalled();
    });

    it('calls getAnnouncements on mount',  () => {
        setWrapper();
        expect(getAnnouncements).toBeCalled();
    });

    it('calls getAllChatsFromUser on mount',  () => {
        setWrapper();
        expect(getAllChatsFromUser).toBeCalled();
    });

    it('check if setChatId is called after click button', () => {
        setWrapper();
        wrapper.find('.btn').trigger('click');
        expect(listener.called);
    });
});