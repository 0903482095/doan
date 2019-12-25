package com.hoangnt.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import com.fasterxml.jackson.databind.util.JSONPObject;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.byteowls.jopencage.JOpenCageGeocoder;
import com.byteowls.jopencage.model.JOpenCageForwardRequest;
import com.byteowls.jopencage.model.JOpenCageLatLng;
import com.byteowls.jopencage.model.JOpenCageResponse;
import com.hoangnt.entity.Address;
import com.hoangnt.entity.City;
import com.hoangnt.entity.District;
import com.hoangnt.entity.Shift;
import com.hoangnt.entity.Stadium;
import com.hoangnt.entity.StatusShift;
import com.hoangnt.entity.Town;
import com.hoangnt.entity.User;
import com.hoangnt.model.AddressDTO;
import com.hoangnt.model.CityDTO;
import com.hoangnt.model.DistrictDTO;
import com.hoangnt.model.InformationUser;
import com.hoangnt.model.Statistical;
import com.hoangnt.model.StatisticalAll;
import com.hoangnt.model.ShiftDTO;
import com.hoangnt.model.StadiumDTO;
import com.hoangnt.model.StadiumImageDTO;
import com.hoangnt.model.StatusShiftResponse;
import com.hoangnt.model.TownDTO;
import com.hoangnt.model.request.RequestAddress;
import com.hoangnt.repository.AddressRepository;
import com.hoangnt.repository.CityRepository;
import com.hoangnt.repository.DistrictRepository;
import com.hoangnt.repository.ShiftRepository;
import com.hoangnt.repository.StadiumRepository;
import com.hoangnt.repository.StatusShiftRepository;
import com.hoangnt.repository.TownRepository;
import com.hoangnt.service.AddressService;
import com.hoangnt.utils.TypeStadium;
import org.springframework.web.client.RestTemplate;

@Service
public class AddressServiceImpl implements AddressService {
	@Autowired
	AddressRepository addressRepository;

	@Autowired
	StadiumRepository stadiumRepository;

	@Autowired
	ShiftRepository shiftRepository;

	@Autowired
	StatusShiftRepository statusShiftRepository;

	@Autowired
	CityRepository cityRepository;

	@Autowired
	DistrictRepository districtRepository;

	@Autowired
	TownRepository townRepository;

	@Autowired
	EntityManager em;

	@Override
	public int addAddress(RequestAddress requestAddress) {
		Address address = new Address();
		address.setUser(new User(requestAddress.getUser()));

		address.setName(requestAddress.getName());
		address.setSpecificAddress(requestAddress.getSpecificAddress());
		address.setDescription(requestAddress.getDescription());
		address.setCity(new City(requestAddress.getMatp()));
		address.setDistrict(new District(requestAddress.getMaqh()));
		address.setTown(new Town(requestAddress.getXaid()));
		String addressName =address.getSpecificAddress()+","+townRepository.findById(requestAddress.getXaid()).get().getName() + ","
				+ districtRepository.findById(requestAddress.getMaqh()).get().getName() + ","
				+ cityRepository.findById(requestAddress.getMatp()).get().getName();
//		JOpenCageLatLng latlng = getLatLong(addressName);
//		address.setLatitude(latlng.getLat());
//		address.setLongitude(latlng.getLng());
		address.setLatitude((Double)getLatLngFromAddressName(addressName).get("lat"));
		address.setLongitude((Double)getLatLngFromAddressName(addressName).get("lng"));

		getLatLngFromAddressName(addressName);
		List<Stadium> stadiums = new ArrayList<Stadium>();

		requestAddress.getStadiumDTOs().forEach(stadiumDTO -> {
			Stadium stadium = new Stadium();
			stadium.setAddress(address);
			stadium.setName(stadiumDTO.getName());
			stadium.setType(stadiumDTO.getMaType());
			stadium.setDescription(stadiumDTO.getDescription());

			List<Shift> shifts = new ArrayList<Shift>();
			requestAddress.getShiftDTOs().forEach(shiftDTO -> {
				Shift shift = new Shift();
				shift.setStadium(stadium);

				shift.setName(shiftDTO.getName());
				shift.setTime_start(shiftDTO.getTime_start());
				shift.setTime_end(shiftDTO.getTime_end());
				shift.setCash(shiftDTO.getCash());
				shifts.add(shift);
			});
			stadium.setShifts(shifts);
			stadiums.add(stadium);
		});
		address.setStadiums(stadiums);
		Address address2 = addressRepository.save(address);
		return address2.getId();
	}

	@Override
	public int updateAddress(RequestAddress requestAddress,int idUser) {
		
		Address address = addressRepository.findById(requestAddress.getId()).get();
		if(address.getUser().getId()==idUser) {
			address.setName(requestAddress.getName());
			address.setSpecificAddress(requestAddress.getSpecificAddress());
			address.setDescription(requestAddress.getDescription());
			address.setCity(new City(requestAddress.getMatp()));
			address.setDistrict(new District(requestAddress.getMaqh()));
			address.setTown(new Town(requestAddress.getXaid()));

			String addressName = townRepository.findById(requestAddress.getXaid()).get().getName() + ", "
					+ districtRepository.findById(requestAddress.getMaqh()).get().getName() + ", "
					+ cityRepository.findById(requestAddress.getMatp()).get().getName();
			JOpenCageLatLng latlng = getLatLong(addressName);
			address.setLatitude(latlng.getLat());
			address.setLongitude(latlng.getLng());
			List<Stadium> stadiums = new ArrayList<Stadium>();

			requestAddress.getStadiumDTOs().forEach(stadiumDTO -> {
				Stadium stadium = stadiumRepository.findById(stadiumDTO.getId()).get();
				stadium.setName(stadiumDTO.getName());
				stadium.setType(stadiumDTO.getMaType());
				stadium.setDescription(stadiumDTO.getDescription());

			});
			address.setStadiums(stadiums);
			Address address2 = addressRepository.save(address);
			return address2.getId();
		}
		return -1;
		
	}

	@Override
	public List<AddressDTO> getAll() {
		List<AddressDTO> addressDTOs = new ArrayList<AddressDTO>();
		addressRepository.findAll().forEach(address -> {
			AddressDTO addressDTO = new AddressDTO();
			addressDTOs.add(entity2DTO(addressDTO, address));
		});
		return addressDTOs;
	}

	@Override
	public List<AddressDTO> getAllByIdUser(int id) {
		List<AddressDTO> addressDTOs = new ArrayList<AddressDTO>();
		addressRepository.getByIdUser(id).forEach(address -> {
			AddressDTO addressDTO = new AddressDTO();
			addressDTOs.add(entity2DTO(addressDTO, address));
		});
		return addressDTOs;
	}

//	@Override
//	public List<AddressDTO> getAllByIdUserWithStatus(int id, int status) {
//		List<AddressDTO> addressDTOs = new ArrayList<AddressDTO>();
//
//		addressRepository.getByIdUser(id).forEach(address -> {
//			AddressDTO addressDTO = new AddressDTO();
//
//			List<StadiumDTO> stadiumDTOs = new ArrayList<StadiumDTO>();
//			stadiumRepository.getByIdAddress(address.getId()).forEach(stadium -> {
//				StadiumDTO stadiumDTO = new StadiumDTO();
//				stadiumDTO.setId(stadium.getId());
//				stadiumDTO.setName(stadium.getName());
//				stadiumDTO.setMaType(stadium.getType());
//				stadiumDTO.setType(TypeStadium.getTypeByValue(stadium.getType()).toString());
//				stadiumDTO.setDescription(stadium.getDescription());
//
//				List<StatusShift> statusShifts = statusShiftRepository.getFullByStatus(status);
//				List<StatusShiftResponse> statusShiftResponses = new ArrayList<>();
//
//				statusShifts.forEach(statusShift -> {
//					if (statusShift.getShift().getStadium().getId() == stadium.getId()) {
//
//						StatusShiftResponse statusShiftResponse = new StatusShiftResponse();
//						statusShiftResponse.setId(statusShift.getId());
//
//						statusShiftResponses.add(statusShiftResponse);
//					}
//				});
//				if (statusShiftResponses.isEmpty()) {
//					stadiumDTO = null;
//				} else {
//					stadiumDTO.setStatusShiftResponses(statusShiftResponses);
//					stadiumDTOs.add(stadiumDTO);
//
//					addressDTO.setStadiumDTOs(stadiumDTOs);
//				}
//			});
//
//			if (null!=addressDTO.getStadiumDTOs()) {
//				addressDTOs.add(entity2DTO(addressDTO, address));
//			}
//		});
//		
//		addressDTOs.forEach(address->{
//			address.getStadiumDTOs().forEach(stadium->{
//				stadium.setStatusShiftResponses(null);
//			});
//		});
//
//		return addressDTOs;
//	}
	

	@Override
	public List<AddressDTO> getListAddressByLatLng(double lat, double lng) {
		List<AddressDTO> addressDTOs = new ArrayList<AddressDTO>();

		List<Address> sortedAddress = addressRepository.findAll().stream().sorted(sortByCoodinates(lat, lng))
				.collect(Collectors.toList());

		sortedAddress.forEach(address -> {
			AddressDTO addressDTO = new AddressDTO();
			addressDTOs.add(entity2DTO(addressDTO, address));
		});
		return addressDTOs;
	}

	AddressDTO entity2DTO(AddressDTO addressDTO, Address address) {
		addressDTO.setId(address.getId());
		addressDTO.setName(address.getName());
		addressDTO.setSpecificAddress(address.getSpecificAddress());
		addressDTO.setDescription(address.getDescription());

		CityDTO cityDTO = new CityDTO();
		cityDTO.setMatp(address.getCity().getMatp());
		cityDTO.setName(address.getCity().getName());
		cityDTO.setType(address.getCity().getType());
		addressDTO.setCity(cityDTO);

		DistrictDTO districtDTO = new DistrictDTO();
		districtDTO.setMaqh(address.getDistrict().getMaqh());
		districtDTO.setName(address.getDistrict().getName());
		districtDTO.setType(address.getDistrict().getType());
		addressDTO.setDistrict(districtDTO);

		TownDTO townDTO = new TownDTO();
		townDTO.setXaid(address.getTown().getXaid());
		townDTO.setName(address.getTown().getName());
		townDTO.setType(address.getTown().getType());
		addressDTO.setTown(townDTO);

		if (address.getStadiumImages() != null) {
			List<StadiumImageDTO> stadiumImageDTOs = new ArrayList<>();
			address.getStadiumImages().forEach(image -> {
				StadiumImageDTO stadiumImageDTO = new StadiumImageDTO();
				stadiumImageDTO.setId(image.getId());
				stadiumImageDTO.setUrlImage(image.getUrlImage());

				stadiumImageDTOs.add(stadiumImageDTO);
			});
			addressDTO.setStadiumImageDTOs(stadiumImageDTOs);
		}
		return addressDTO;
	}

	@Override
	public int deleteAddress(int id, int idUser) {

		if (addressRepository.findById(id).get().getUser().getId() == idUser) {
			addressRepository.deleteById(id);
			return id;
		}
		return -1;

	}
	
	@Override
	public StatisticalAll<Float> profitDateAddressByIdUserWithStatus(int id,int status,String date) {
		
		StatisticalAll<Float> statisticalAll=new StatisticalAll<Float>();
		statisticalAll.setDate(date);
		List<Statistical<Float>> statisticals=new ArrayList<>();
		float sumAllAddress=0;
		List<Address> address=addressRepository.getByIdUser(id);
		
		for(int k=0;k<address.size();k++) {
			Statistical<Float> statistical=new Statistical<Float>();
			float sumAddress=0;

			List<Stadium> stadiums=stadiumRepository.getByIdAddress(address.get(k).getId());
			for(int j=0;j<stadiums.size();j++) {
				
				List<StatusShift> statusShifts = statusShiftRepository.getFullByStatusAndDate(status,date);				
				for (int i = 0; i < statusShifts.size(); i++) {
					if (statusShifts.get(i).getShift().getStadium().getId() == stadiums.get(j).getId()) {
						sumAddress += statusShifts.get(i).getShift().getCash();
					}
				}
			}
			sumAllAddress+=sumAddress;
			
			statistical.setNameAddess(address.get(k).getName());
			statistical.setValue(sumAddress);
			statisticals.add(statistical);
		}
		statisticalAll.setStatisticals(statisticals);
		statisticalAll.setTotal(sumAllAddress);

		return statisticalAll;
	}

	@Override
	public StatisticalAll<Integer> numberShiftDateAddressByIdUserWithStatus(int id, int status, String date) {
		StatisticalAll<Integer> statisticalAll=new StatisticalAll<Integer>();
		statisticalAll.setDate(date);
		List<Statistical<Integer>> statisticals=new ArrayList<>();
		int sumAllAddress=0;
		List<Address> address=addressRepository.getByIdUser(id);
		
		for(int k=0;k<address.size();k++) {
			Statistical<Integer> statistical=new Statistical<Integer>();
			int sumAddress=0;

			List<Stadium> stadiums=stadiumRepository.getByIdAddress(address.get(k).getId());
			for(int j=0;j<stadiums.size();j++) {
				
				List<StatusShift> statusShifts = statusShiftRepository.getFullByStatusAndDate(status,date);				
				for (int i = 0; i < statusShifts.size(); i++) {
					if (statusShifts.get(i).getShift().getStadium().getId() == stadiums.get(j).getId()) {
						sumAddress ++;
					}
				}
			}
			sumAllAddress+=sumAddress;
			
			statistical.setNameAddess(address.get(k).getName());
			statistical.setValue(sumAddress);
			statisticals.add(statistical);
		}
		statisticalAll.setStatisticals(statisticals);
		statisticalAll.setTotal(sumAllAddress);

		return statisticalAll;
	}
	
	@Override
	public StatisticalAll<Integer> numberShiftDateAddressByIdUserWithStatus0(int id, String date) {
		StatisticalAll<Integer> statisticalAll=new StatisticalAll<Integer>();
		statisticalAll.setDate(date);
		List<Statistical<Integer>> statisticals=new ArrayList<>();
		int sumAllAddress=0;
		List<Address> address=addressRepository.getByIdUser(id);
		
		for(int k=0;k<address.size();k++) {
			Statistical<Integer> statistical=new Statistical<Integer>();
			
			int sumAddressWithStatus0=0;

			List<Stadium> stadiums=stadiumRepository.getByIdAddress(address.get(k).getId());
			for(int j=0;j<stadiums.size();j++) {
				int sumAddress=0;
				
				List<StatusShift> statusShifts = statusShiftRepository.getAllStatusShiftByDate(date);				
				for (int i = 0; i < statusShifts.size(); i++) {
					if (statusShifts.get(i).getShift().getStadium().getId() == stadiums.get(j).getId()) {
						sumAddress ++;
					}
				}
				sumAddressWithStatus0+=stadiums.get(j).getShifts().size()-sumAddress;
			}
			
			
			sumAllAddress+=sumAddressWithStatus0;
			
			statistical.setNameAddess(address.get(k).getName());
			statistical.setValue(sumAddressWithStatus0);
			statisticals.add(statistical);
		}
		statisticalAll.setStatisticals(statisticals);
		statisticalAll.setTotal(sumAllAddress);

		return statisticalAll;
	}

	public JSONObject getLatLngFromAddressName(String addressName){
		RestTemplate restTemplate=new RestTemplate();
		String urlGetLatLng="https://maps.googleapis.com/maps/api/geocode/json?address="+addressName+"&key=AIzaSyCkK0QC1EO9-If0sumMn3ED_5BuwiUNTHs";
		ResponseEntity<String> response
				= restTemplate.getForEntity(urlGetLatLng , String.class);

		JSONObject jsonObject=new JSONObject(response.getBody());
		JSONArray jsonArray= (JSONArray) jsonObject.get("results");

		JSONObject jsonObject1= (JSONObject) jsonArray.get(0);
		JSONObject jsonObjectGeometry= (JSONObject) jsonObject1.get("geometry");
		JSONObject jsonObjectLocation= (JSONObject) jsonObjectGeometry.get("location");

		return jsonObjectLocation;
	}


	public JOpenCageLatLng getLatLong(String addressName) {
		JOpenCageGeocoder jOpenCageGeocoder = new JOpenCageGeocoder("098126ec84a8428c82f7afcdcfe69b5a");
		JOpenCageForwardRequest request = new JOpenCageForwardRequest(addressName);
		request.setRestrictToCountryCode("VN"); // restrict results to a specific country
		// request.setBounds(18.367, -34.109, 18.770, -33.704); // restrict results to a
		// geographic bounding box (southWestLng, southWestLat, northEastLng,
		// northEastLat)

		JOpenCageResponse response = jOpenCageGeocoder.forward(request);
		JOpenCageLatLng firstResultLatLng = response.getFirstPosition(); // get the coordinate pair of the first result

		return firstResultLatLng;
	}

	private Comparator<Address> sortByCoodinates(double currentLat, double currentLng) {
		Comparator<Address> compareByName = Comparator.comparing(
				(Address address) -> getDistanceFromLatLonInKm(currentLat,currentLng,address.getLatitude(),address.getLongitude()));
		return compareByName;
	}

	private Double getDistanceFromLatLonInKm(double currentLat, double currentLng,double otherLat, double otherLng) {
		int R = 6371; // Radius of the earth in km
		double dLat = deg2rad(otherLat-currentLat);  // deg2rad below
		double dLon = deg2rad(otherLng-currentLng);
		double a =
				Math.sin(dLat/2) * Math.sin(dLat/2) +
						Math.cos(deg2rad(currentLat)) * Math.cos(deg2rad(otherLat)) *
								Math.sin(dLon/2) * Math.sin(dLon/2)
				;
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		double d = R * c; // Distance in km
		return d;
	}

	private Double deg2rad(Double deg) {
		return deg * (Math.PI/180);
	};

	
}