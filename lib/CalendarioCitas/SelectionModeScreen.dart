import 'package:flutter/material.dart';
import 'CustomCalendarView.dart';
import 'times_model.dart';

class SelectionModeScreen extends StatelessWidget {
  const SelectionModeScreen({super.key});

  @override
  Widget build(BuildContext context) {
    final Map<String, TimesModel> dummyTimeslots = {
      "Monday": TimesModel(isOpen: true, openTime: "09:00 AM", closeTime: "06:00 PM"),
      "Tuesday": TimesModel(isOpen: true, openTime: "09:00 AM", closeTime: "06:00 PM"),
      "Wednesday": TimesModel(isOpen: true, openTime: "09:00 AM", closeTime: "06:00 PM"),
      "Thursday": TimesModel(isOpen: true, openTime: "09:00 AM", closeTime: "06:00 PM"),
      "Friday": TimesModel(isOpen: true, openTime: "09:00 AM", closeTime: "06:00 PM"),
      "Saturday": TimesModel(isOpen: true),
      "Sunday": TimesModel(isOpen: false), // Closed on Sundays
    };

    return Scaffold(
      backgroundColor: Colors.black,
      appBar: AppBar(
        backgroundColor: Colors.black,
        foregroundColor: Colors.white,
        title: const Text('Selection Mode'),
      ),
      body: SafeArea(
        child: Center(
          child: Padding(
            padding: const EdgeInsets.symmetric(horizontal: 32.0),
            child: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                _buildModeButton(
                  context,
                  'Single Day Selection',
                  () => CustomCalendarView(multiDay: false),
                ),
                const SizedBox(height: 24),
                _buildModeButton(
                  context,
                  'Multi-Day Selection',
                  () => CustomCalendarView(multiDay: true),
                ),
                const SizedBox(height: 24),
                _buildModeButton(
                  context,
                  'With Business Hours',
                  () => CustomCalendarView(
                    multiDay: true,
                    timeslots: dummyTimeslots,
                  ),
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }

  Widget _buildModeButton(BuildContext context, String title, Widget Function() builder) {
    return SizedBox(
      width: double.infinity,
      child: ElevatedButton(
        onPressed: () => Navigator.push(
          context,
          MaterialPageRoute(builder: (context) => builder()),
        ),
        style: ElevatedButton.styleFrom(
          backgroundColor: Colors.white,
          foregroundColor: Colors.black,
          padding: const EdgeInsets.symmetric(vertical: 16),
          shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(12)),
        ),
        child: Text(title, style: const TextStyle(fontWeight: FontWeight.bold)),
      ),
    );
  }
}