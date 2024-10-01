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

  createCar(model: string, brand: string, engineType: string): Observable<any> {

    const car: Car = {
      id: undefined, // Or set it to a default value if required
      model: model,
      brand: brand,
      engineType: engineType
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
}
