package com.groupeone.ferme.utils;

public interface RequestListener<T> {
  void onResponse(T result);
}
