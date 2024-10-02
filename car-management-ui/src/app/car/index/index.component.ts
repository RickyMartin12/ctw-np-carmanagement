import { Component } from '@angular/core';

import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { CarService } from '../../service/car.service';
import { Car } from '../../model/car';
import { FormsModule,NgForm  } from '@angular/forms'; // Import FormsModule
import { MenuComponent } from '../../menu/menu.component'; // Import the MenuComponent

@Component({
  selector: 'app-index',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule, MenuComponent],
  templateUrl: './index.component.html',
  styleUrl: './index.component.css'
})
export class IndexComponent {

  cars: Car[] = [];
  searchBrand: string = '';  // Search input for brand
  searchModel: string = '';  // Search input for model
  filteredCarList: Car[] = [];  // To store the filtered cars

  constructor(public carService: CarService) { }
      
  /**
   * Write code on Method
   *
   * @return response()
   */
  ngOnInit(): void {
    this.loadCars(); 
  }

  filterCars(): void {
    this.filteredCarList = this.cars.filter(car => {
      return (this.searchBrand === '' || car.brand.toLowerCase().includes(this.searchBrand.toLowerCase())) &&
             (this.searchModel === '' || car.model.toLowerCase().includes(this.searchModel.toLowerCase()));
    });
    console.log( this.filteredCarList);
  }

  loadCars(): void {
    this.carService.getAll().subscribe((data: Car[])=>{
      this.cars = data;
      this.filteredCarList = data;  // Initialize with all cars
    });
  }

  deleteCar(carId: string | undefined): void {
    if (confirm('Are you sure you want to delete this car with id '+carId+'?')) { // Confirm before deletion
      this.carService.deleteCar(carId).subscribe({
        next: (response) => {
          console.log('Car deleted successfully!', response);
          this.loadCars(); // Reload the cars after deletion to update the list
        },
        error: (error) => {
          console.error('There was an error deleting the car!', error);
        }
      });
    }
  }

  
}
