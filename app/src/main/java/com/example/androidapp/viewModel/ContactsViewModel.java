//package com.example.androidapp.viewModel;
//
//import androidx.lifecycle.LiveData;
//import androidx.lifecycle.ViewModel;
//
//import com.example.androidapp.Contact;
//import com.example.androidapp.repositories.ContactRepository;
//
//import java.util.List;
//
//public class ContactsViewModel extends ViewModel {
//
//    private ContactRepository cRepo;
//    private LiveData<List<Contact>> contacts;
//
//    public ContactsViewModel() {
//        cRepo = new ContactRepository();
//        contacts = ContactRepository.getAll();
//    }
//    public LiveData<List<Contact>> get(){return contacts;}
//
//}
