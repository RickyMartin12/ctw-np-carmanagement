import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router'; // Import Router
import { CarService } from '../../service/car.service';
import { CommonModule } from '@angular/common';
import { RouterModule  } from '@angular/router';
import { FormsModule,NgForm  } from '@angular/forms'; // Import FormsModule
import { MenuComponent } from '../../menu/menu.component'; // Import the MenuComponent
import { DatePipe } from '@angular/common';


@Component({
  selector: 'app-create-booking',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule, MenuComponent],
  templateUrl: './create-booking.component.html',
  styleUrl: './create-booking.component.css',
  providers: [DatePipe] 
})
export class CreateBookingComponent implements OnInit {

  name_person: string = '';  // Ensure this is a string, not an object
  location: string = '';
  contact_number: string = '';
  license_number: string = '';
  data_hour: string = '';
  data_hour_fim: string = '';
  car_id: string = '';

  // To hold validation messages
  validationMessages: string[] = [];
  successMessage: string = ''; // Initialize the success message

  listCars: any[] = [];
  selectedCar: string = ''; // Variable to hold selected car ID

  minDateTime: string = ''; // Will hold the min date for the datetime-local input
  minEndDateTime: string = '';
  maxDateTime: string = ''; // Maximum selectable date for the end date (optional)

  stringValue: string = '';

  reservations: any[] = []; // To store the list of reservations
  carByIds: number[] = []; // To store the car IDs from reservations
  

  constructor(public carService: CarService, private datePipe: DatePipe, private router: Router) { }



  ngOnInit(): void {

    const now = new Date();
    this.minDateTime = now.toISOString().slice(0, 16); 
    //this.minEndDateTime = now.toISOString().slice(0, 16); 
    this.maxDateTime = now.toISOString().slice(0, 16);

    this.carService.getAllCarsSelected().subscribe({
      next: (data) => { this.listCars = data; console.log(data); },
      error: (error) => { console.error(error); }
    });

    this.carService.getAllReservations().subscribe({
      next: (data) => {
        this.reservations = data; // Store the response
        this.carByIds = this.reservations.map(reservation => reservation.car_id); // Extract car IDs
        //console.log(this.carByIds); // Log the array of car IDs
      },
      error: (error) => {
        console.error('Error fetching reservations:', error); // Handle error
      }
    }) 

    

  }

  onStartDateChange(): void {
    console.log(this.data_hour);
    if (this.data_hour) {
      const startDate = new Date(this.data_hour);
      this.minEndDateTime = startDate.toISOString().slice(0, 16); // Set the min end date to the start date
    } else {
      this.minEndDateTime = this.minDateTime; // Fallback to the default min date if no start date is selected
    }
  }

  validateDate(date: any) {
    const day = date.getDay(); // Get day of the week (0 = Sunday, 1 = Monday, ..., 6 = Saturday)
    const hours = date.getHours(); // Get hours of the day
    
    // Check if the day is Friday (5) after 20:00 or before Monday (1) 08:00
    if ((day === 5 && hours >= 20) || (day === 6) || (day === 0) || (day === 1 && hours <= 8)) {
      return true;
    }
    return false;
  }

  onCarChange(event: any): void {
    const selectedCarId = event.target.value;
    console.log('Selected car ID:', selectedCarId);

    // You can also perform any other actions with the selected car ID here
  }

  timeToSeconds(timeString: any) {
    const [hours, minutes, seconds] = timeString.split(':').map(Number);
    return hours * 3600 + minutes * 60 + seconds;
  }

  onSubmit(reservationForm: any): void {

    let flag = 0;

    this.validationMessages = []; // Clear previous messages

    const selectedDate = new Date(this.data_hour);
    const selectedDateFim = new Date(this.data_hour_fim);


    if (!reservationForm.valid) {
      if (!this.name_person) {
        this.validationMessages.push('Name Person is required.');
      }
      if (!this.location) {
        this.validationMessages.push('Location is required.');
      }
      if (!this.contact_number) {
        this.validationMessages.push('Contact Number is required.');
      }
      if (!this.license_number) {
        this.validationMessages.push('License Number is required.');
      }
      if (!this.car_id) {
        this.validationMessages.push('CAR ID is required.');
      }
      if (!this.data_hour) {
        this.validationMessages.push('Start Data Hour is required.');
      }
      if (!this.data_hour_fim) {
        this.validationMessages.push('End Data Hour is required.');
      }
      
      return; // Stop form submission if invalid
    }
    else {
      if (selectedDateFim <= selectedDate) {
        // Show error if end date is earlier or equal to start date
        this.validationMessages.push('End date and time must be after the start date and time.');
      }
      else {
        if (this.validateDate(selectedDate) && this.validateDate(selectedDateFim)) {

          for (let i = 0; i < this.reservations.length; i++) {
            // Convert dateHour and dateHourFim from string to Date objects
            var data_hora_inicio_bd = new Date(this.reservations[i].dateHour);
            var data_hora_fim_bd = new Date(this.reservations[i].dateHourFim);

            if(this.car_id == this.reservations[i].car_id) {
              if((selectedDate <= data_hora_inicio_bd && selectedDateFim <= data_hora_inicio_bd) || (data_hora_fim_bd <= selectedDate && data_hora_fim_bd <= selectedDateFim))
                {
                  flag = 0;
                }
                else {
                  flag = 1;
                  break;
                }    
            }
        
            
          }

          if (flag == 1) {
            this.validationMessages.push("The Car cannot be booked between the dates. Choose another date our another car to booked");
          }
          else {

            const reservationData = {
              name: this.name_person,
              location: this.location,
              contactNumber: this.contact_number,
              licenseNumber: this.license_number,
              dataInicio: this.data_hour,
              dataFim: this.data_hour_fim,
              carId: this.car_id
            };

            //console.log(reservationData);

            this.carService.createReservation(reservationData).subscribe({
              next: (response) => {
                console.log('Reservation created successfully:', response);
                this.successMessage = 'Reservation '+response.name+' with car ID '+response.carId+' created successfully!';

                setTimeout(() => {
                  this.router.navigate(['/booking']); // Redirect to home page on success
                }, 5000); 
                //this.router.navigate(['/booking']); // Redirect to home page on success
              },
              error: (error) => {
                  console.error('There was an error!', error);
              }
          });

            
            
          }

          
        } else {
          this.validationMessages.push("The Dates and times must be between Friday 20:00 and Monday 08:00.");
        }
      }
    }
  
    

    
    /*if (!reservationForm.valid) {
      if (!this.name_person) {
        this.validationMessages.push('Name Person is required.');
      }
      if (!this.location) {
        this.validationMessages.push('Location is required.');
      }
      if (!this.car_id) {
        this.validationMessages.push('CAR ID is required.');
      }
      
      return; // Stop form submission if invalid
    }
    else {*/
      
      

      
    //}
  }

}
