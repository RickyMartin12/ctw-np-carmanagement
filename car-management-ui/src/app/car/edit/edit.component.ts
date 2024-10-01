import { Component, OnInit } from '@angular/core';
import { CarService } from '../../service/car.service';
import { Car } from '../../model/car';
import { CommonModule } from '@angular/common';
import { RouterModule, ActivatedRoute, Router } from '@angular/router';
import { FormsModule,NgForm  } from '@angular/forms'; // Import FormsModule

@Component({
  selector: 'app-edit',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './edit.component.html',
  styleUrls: ['./edit.component.css'] // Fix typo: styleUrl -> styleUrls
})
export class EditComponent implements OnInit {

  car: Car = { id: '', brand: '', model: '', engineType: '' };
  id: string | null = '';
  validationMessages: string[] = [];
  successMessage: string = ''; // For displaying success messages

  constructor(private route: ActivatedRoute, public carService: CarService, private router: Router) { }

  ngOnInit(): void {
    // Get the car ID from the route
    const id: string = this.route.snapshot.paramMap.get('carId') || '';

    // Fetch car details using the ID
    this.carService.getCarById(id).subscribe({
      next: (data) => { this.car = data },
      error: (error) => { console.error(error); }
    });
  }

  onSubmit(carForm: any): void {
    this.validationMessages = []; // Clear previous messages

    if (!carForm.valid) {
      if (!this.car.brand) {
        this.validationMessages.push('Brand is required.');
      }
      if (!this.car.model) {
        this.validationMessages.push('Model is required.');
      }
      if (!this.car.engineType) {
        this.validationMessages.push('Engine Type is required.');
      }
      return; // Stop form submission if invalid
    }
    else {
      this.carService.updateCar(this.car).subscribe({
        next: (response) => {
          this.successMessage = 'Car updated successfully!';
          // Optionally redirect after a delay or immediately
          setTimeout(() => {
            this.router.navigate(['/']); // Redirect to car list or another page
          }, 2000); // Redirect after 2 seconds
        },
        error: (error) => {
          console.error('Error updating car:', error);
        }
      });
    }

    


  }
}
