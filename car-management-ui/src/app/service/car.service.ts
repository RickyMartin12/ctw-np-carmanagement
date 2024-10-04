import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';

import {  Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { Car } from '../model/car';

@Injectable({
  providedIn: 'root'
})
export class CarService {

  private apiURL = "http://localhost:8080"

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  }

  constructor(private httpClient: HttpClient) { }

  /**
   * Write code on Method
   *
   * @return response()
   */
  getAll(): Observable<any> {
  
    return this.httpClient.get(this.apiURL + '/car')
  
    .pipe(
      catchError(this.errorHandler)
    )
  }

  getCarById(id: string): Observable<any> {
    return this.httpClient.get(`${this.apiURL}/car/${id}`);
  }

  getCarByIdString(id: string): Observable<any> {
    return this.httpClient.get(`${this.apiURL}/car/string/${id}`);
  }

  createCar(model: string, brand: string, engineType: string): Observable<any> {

    const car: Car = {
      id: undefined, // Or set it to a default value if required
      model: model,
      brand: brand,
      engineType: engineType,
      image: '',
      color: ''
    };
    // Make a POST request with query parameters
    return this.httpClient.post(`${this.apiURL}/car/create?model=${model}&brand=${brand}&engineType=${engineType}`, car, { headers: this.httpOptions.headers })
      .pipe(
        catchError(this.errorHandler)
      );
  }

  deleteCar(carId: string | undefined): Observable<any> {
    return this.httpClient.delete(`${this.apiURL}/car/delete/${carId}`); // Adjust the endpoint accordingly
  }

  updateCar(car: Car): Observable<any> {
    return this.httpClient.put<any>(`${this.apiURL}/car/${car.id}/edit`, car, this.httpOptions);
  }
  

  createCar2(carData: FormData): Observable<any> {
    return this.httpClient.post(`${this.apiURL}/car/create`, carData);
  }

  updateCar2(carId: string | undefined, car: FormData): Observable<any> {
    return this.httpClient.put<any>(`${this.apiURL}/car/${carId}/edit`, car);
  }


  /** 
   * Write code on Method
   *
   * @return response()
   */
  errorHandler(error:any) {
    let errorMessage = '';
    errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
    return throwError(errorMessage);
  }

  getAllReservations(): Observable<any> {
  
    return this.httpClient.get(this.apiURL + '/reservation')
  
    .pipe(
      catchError(this.errorHandler)
    )
  }

  getReservationById(rersId: number): Observable<any> {
    return this.httpClient.get(`${this.apiURL}/reservation/${rersId}`);
  }

  getAllCarsSelected(): Observable<any> {
  
    return this.httpClient.get(this.apiURL + '/car/listAll')
  
    .pipe(
      catchError(this.errorHandler)
    )
  }

  createReservation(reservationData: any): Observable<any> {
    return this.httpClient.post(`${this.apiURL}/reservation/create`, reservationData, this.httpOptions);
  }

  deleteReservation(rersId: string | undefined): Observable<any> {
    let num: number = Number(rersId);
    return this.httpClient.delete(`${this.apiURL}/reservation/delete/${num}`); // Adjust the endpoint accordingly
  }

  updateReservation(rersId: number | undefined, reservationData: any): Observable<any> {
    console.log(rersId);
    return this.httpClient.put<any>(`${this.apiURL}/reservation/${rersId}/edit`, reservationData, this.httpOptions);
  }

}
