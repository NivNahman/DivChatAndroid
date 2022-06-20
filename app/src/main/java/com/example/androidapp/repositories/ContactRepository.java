//package com.example.androidapp.repositories;
//import android.widget.Toast;
//
//import androidx.lifecycle.LiveData;
//import androidx.lifecycle.MutableLiveData;
//
//import com.example.androidapp.AppDB;
//import com.example.androidapp.Contact;
//import com.example.androidapp.ContactDao;
//import com.example.androidapp.ContactList;
//import com.example.androidapp.Message;
//import com.example.androidapp.MessageDao;
//import com.example.androidapp.api.PostAPI;
//import com.example.androidapp.api.WebServiceAPI;
//
//import java.util.LinkedList;
//import java.util.List;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//public class ContactRepository {
//    private contactListData contactListData;
//    private ContactDao contactDao;
//    private MessageDao messageDao;
//    private PostAPI api;
//    private String UsernameID;
//
//    public ContactRepository(ContactDao contactDao, MessageDao messageDao,String username) {
//        this.contactDao = contactDao;
//        this.messageDao = messageDao;
//        this.UsernameID = username;
//    }
//
//    public ContactRepository() {
//    }
//
//    class contactListData extends MutableLiveData<List<Contact>>{
//        public contactListData(){
//            super();
//            setValue(new LinkedList<>());
//        }
//        @Override
//        protected void onActive(){
//            super.onActive();
//
////            new Thread(() -> {
////                contactListData.postValue(contactDao.get());
////            }).start();
//            PostAPI postAPI = new PostAPI();
//            WebServiceAPI webServiceAPI = postAPI.getWebServiceAPI();
//            Call<List<Contact>> call = webServiceAPI.getcontacts(username);
//            call.enqueue(new Callback<List<Contact>>() {
//                @Override
//                public void onResponse(Call<List<Contact>> call, Response<List<Contact>> response) {
//                    List<Contact> contacts = response.body();
//                    AppDB.clearRoomDB();
//                    for (Contact contact : contacts) {
//                        contactDao.insert(contact);
//                        Call<List<Message>> call2 = webServiceAPI.getmessages(contact.getId(), username);
//                        call2.enqueue(new Callback<List<Message>>() {
//                            @Override
//                            public void onResponse(Call<List<Message>> call2, Response<List<Message>> response2) {
//                                List<Message> messages = response2.body();
//                                for (Message message : messages) {
//                                    message.setContactID(contact.getId());
//                                    messageDao.insert(message);
//                                }
//                            }
//
//                            @Override
//                            public void onFailure(Call<List<Message>> call2, Throwable t) {
//                                //Toast.makeText(ContactRepository.this, "Failed to contact with the server", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//
//                    }
//                }
//                @Override
//                public void onFailure(Call<List<Contact>> call, Throwable t) {
//                    //Toast.makeText(ContactList.this, "Failed to contact with the server", Toast.LENGTH_SHORT).show();
//                }
//            });
//        }
//        }
//        public LiveData<List<Contact>> getall(){
//        return contactListData;
//        }
//
//
//
//    }
