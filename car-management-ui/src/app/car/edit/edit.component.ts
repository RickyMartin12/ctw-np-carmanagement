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

  car: Car = { id: '', brand: '', model: '', engineType: '', color: '', image: '' };
  id: string | null = '';
  validationMessages: string[] = [];
  successMessage: string = ''; // For displaying success messages
  selectedFile: File | null = null;  // Variable to store the selected file

  onFileSelected(event: any): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      const file = input.files[0]; // Get the selected file
      const modifiedFileName = this.normalizeFileName(file.name);
      console.log(modifiedFileName);
      this.selectedFile = new File([file], modifiedFileName, { type: file.type });
    }
  }

  normalizeFileName(fileName: string): string {
    return fileName
        .replace(/[^a-zA-Z0-9-_\. ]+/g, '')  // Remove special characters, keeping alphanumeric, hyphens, underscores, periods, and spaces
        .replace(/\s+/g, '-')                 // Replace spaces with hyphens
        .toLowerCase();                       // Convert to lower case
}

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
      if (!this.car.color) {
        this.validationMessages.push('Color car is required.');
      }
      return; // Stop form submission if invalid
    }
    else {

      const formData = new FormData();
        console.log(this.selectedFile);
      formData.append('brand', this.car.brand);
      formData.append('model', this.car.model);
      formData.append('engineType', this.car.engineType);
      formData.append('color', this.car.color);
      if (this.selectedFile) {
        formData.append('image', this.selectedFile, this.selectedFile.name);  // Add image file to FormData
        formData.append('fileName', this.selectedFile.name)
      }

      console.log(this.car.id);

       // Now you can call the createCar method
       this.carService.updateCar2(this.car.id, formData).subscribe({
        next: (response) => {
          this.successMessage = 'Car with ID '+this.car.id+' updated successfully!';
            //console.log('Car created successfully!', response);
            setTimeout(() => {
              this.router.navigate(['/']); // Redirect to home page on success
            }, 5000); // 5000 milliseconds = 5 seconds
        },
        error: (error) => {
            console.error('There was an error!', error);
        }
    });


      /*this.carService.updateCar(this.car).subscribe({
        next: (response) => {
          this.successMessage = 'Car with ID '+this.car.id+' updated successfully!';
          // Optionally redirect after a delay or immediately
          setTimeout(() => {
            this.router.navigate(['/']); // Redirect to car list or another page
          }, 2000); // Redirect after 2 seconds
        },
        error: (error) => {
          console.error('Error updating car:', error);
        }
      });*/
    }

    


  }
}
